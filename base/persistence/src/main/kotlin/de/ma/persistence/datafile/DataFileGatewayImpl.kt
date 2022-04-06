package de.ma.persistence.datafile

import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam
import de.ma.persistence.datafile.data.*
import de.ma.persistence.folder.data.FolderCreateDTO
import de.ma.persistence.folder.data.FolderEntity
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.folder.data.FolderSearchParamsDTO
import de.ma.persistence.shared.PagedListImpl
import de.ma.persistence.shared.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository,
    private val folderRepository: FolderRepository
) : DataFileGateway {

    override suspend fun updateDataFile(dataFileUpdate: DataFileUpdate): Result<DataFileShow> {
        val dataFileEntity = (dataFileRepository.findById(dataFileUpdate.id.toEntity()).awaitSuspending()
            ?: return Result.failure(RuntimeException("DataFile with id ${dataFileUpdate.id} not found")))

        if (dataFileEntity.version != dataFileUpdate.version) {
            return Result.failure(RuntimeException("DataFile with id ${dataFileUpdate.id} has been changed"))
        }

        if (dataFileUpdate.name != null) {
            dataFileEntity.name = dataFileUpdate.name!!
        }

        if (dataFileUpdate.extension != null) {
            dataFileEntity.extension = dataFileUpdate.extension!!
        }

        return try {
            Result.success(dataFileRepository.persist(dataFileEntity).awaitSuspending().toShow())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun findById(nanoId: NanoId): Result<DataFileShow> {
        val dataFileEntity = dataFileRepository.findById(nanoId.toEntity()).awaitSuspending() ?: return Result.failure(
            RuntimeException("DataFile not found")
        )
        return Result.success(DataFileShowDTO(dataFileEntity.extension, dataFileEntity.name, nanoId))
    }


    override suspend fun deleteById(nanoId: NanoId): Result<Unit> {
        val updated = try {
            dataFileRepository.update("deleted = true where id = ?1", nanoId.toEntity()).awaitSuspending()
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return if (updated == 1) Result.success(Unit) else Result.failure(RuntimeException("DataFile not found"))
    }

    override suspend fun exists(name: String, extension: String): Boolean {
        return dataFileRepository.find(
            "name = :name and extension =: extension", mapOf(
                "name" to name,
                "extension" to extension
            )
        ).firstResult<DataFileEntity>().awaitSuspending() != null
    }


    override suspend fun save(dataFileCreate: DataFileCreate, folderId: NanoId): Result<NanoId> {


        return Result.success( Panache.withTransaction {
            folderRepository.findById(folderId.toEntity())
                .chain { folder ->
                    val entity = dataFileCreate.toEntity()
                    entity.folder = folder
                    dataFileRepository.persist(entity)
                }
        }.awaitSuspending().id?: throw RuntimeException("DataFile not saved"))

    }


    override suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: DataFileSearchParams?,
        sortParams: SortParam?
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

    override suspend fun purge(nanoId: NanoId): Boolean {
        return dataFileRepository.delete("data_file_id", nanoId.id).awaitSuspending() == 1L
    }


    override suspend fun recover(nanoId: NanoId): Boolean {
        return dataFileRepository.update(
            "update from DataFileEntity set deleted = false where data_file_id = :id",
            mapOf("id" to nanoId.id)
        ).awaitSuspending() == 1
    }

    override suspend fun deleteByFolderId(folderId: NanoId): Result<Unit> {
        val result = try {
            dataFileRepository.update("deleted = true where folder_folder_id = ?1", folderId.toEntity().id)
                .awaitSuspending()
        } catch (e: Exception) {
            return Result.failure(e)
        }
        if (result == 0) {
            return Result.failure(RuntimeException("No data files found"))
        }
        return Result.success(Unit)
    }

}
