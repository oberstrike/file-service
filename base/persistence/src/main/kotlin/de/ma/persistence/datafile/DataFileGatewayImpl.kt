package de.ma.persistence.datafile

import de.ma.domain.datafile.DataFile
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.FolderGateway
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.datafile.data.DataFileRepository
import de.ma.persistence.datafile.data.DataFileShowDTO
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.folder.data.FolderCreateDTO
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.folder.data.FolderSearchParamsDTO
import de.ma.persistence.shared.PagedListImpl
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository,
    private val folderGateway: FolderGateway,
    private val folderRepository: FolderRepository
) : DataFileGateway {

    override suspend fun findById(nanoId: NanoId): Result<DataFileShow> {
        val dataFileEntity = dataFileRepository.findById(nanoId.toEntity()).awaitSuspending() ?: return Result.failure(
            RuntimeException("DataFile not found")
        )
        return Result.success(DataFileShowDTO(dataFileEntity.extension, dataFileEntity.name, nanoId))
    }

    override suspend fun deleteById(nanoId: NanoId): Result<DataFile> {
        val nanoIdEntity = nanoId.toEntity()

        val dataFileEntity =
            dataFileRepository.find("data_file_id", nanoIdEntity.id).firstResult<DataFileEntity>().awaitSuspending()
                ?: return Result.failure(RuntimeException(nanoIdEntity.id))

        dataFileEntity.deleted = true

        return try {
            Result.success(
                dataFileRepository.persist(dataFileEntity).awaitSuspending()
            )
        } catch (e: Exception) {
            Result.failure(RuntimeException("Could not delete data file in database"))
        }
    }

    override suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFile> {

        var folderResult = folderGateway.getFolderByParams(
            FolderSearchParamsDTO(name = dataFileCreate.domain, id = null))

        if(folderResult.isFailure){
           folderResult = folderGateway.createFolder(FolderCreateDTO(dataFileCreate.domain))
        }

        val folder = folderResult.getOrNull()?: return Result.failure(RuntimeException("Could not create folder"))

        val entity = dataFileCreate.toEntity(folderRepository.findByName(folder.name)!!)

        return try {
            Result.success(
                dataFileRepository.persist(entity).awaitSuspending()
            )
        } catch (e: Exception) {
            Result.failure(RuntimeException("Could not save data file in database"))
        }


    }


    override suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileShow>> {
        var sort: Sort? = null

        if (sortParams != null) {
            //Todo implement own sorting mechanism
            sort = Sort.by(sortParams.sortBy, Sort.Direction.valueOf(sortParams.direction))
        }

        val allDataFiles: PanacheQuery<DataFileShowDTO> = dataFileRepository.find(
            sort, searchParams
        ).project(
            DataFileShowDTO::class.java
        )

        val targetPage = allDataFiles.page<DataFileShowDTO>(Page.of(pagedParams.page, pagedParams.size))
        val pageCount = targetPage.pageCount().awaitSuspending()
        val content = targetPage.list<DataFileShowDTO>().awaitSuspending()

        val pagedList: PagedList<DataFileShow> = PagedListImpl(
            pagedParams.page,
            pageCount,
            content
        )
        return Result.success(pagedList)
    }

    override suspend fun purge(dataFile: DataFile) {
        dataFileRepository.delete("data_file_id", dataFile.id!!.id).awaitSuspending()
    }


    override suspend fun recover(dataFile: DataFile) {
        val entity = dataFile as DataFileEntity
        entity.deleted = false

        dataFileRepository.persist(entity).awaitSuspending()
    }

    override suspend fun exists(name: String, extension: String, domain: String): Boolean {
        val dataFileEntities =
            dataFileRepository.find("name = ?1 and extension = ?2 and domain = ?3", name, extension, domain)
                .list<DataFileEntity>()
                .awaitSuspending()
        return dataFileEntities.isNotEmpty()
    }


}
