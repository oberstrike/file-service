package de.ma.persistence.datafile

import de.ma.domain.datafile.*
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import de.ma.persistence.shared.PagedListImpl
import de.ma.persistence.shared.toPagedList
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository,
) : DataFileGateway {

    override suspend fun find(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow> {
        val nanoId = dataFileSearchParams.id
        val dataFile = dataFileRepository.findById(nanoId.toEntity()).awaitSuspending() ?: return Result.failure(
            DataFileException.NotFoundException(nanoId.value)
        )
        return Result.success(DataFileShowDTO(dataFile.extension, dataFile.name, dataFileSearchParams.domain))
    }

    override suspend fun delete(dataFileDelete: DataFileSearchParams): Result<DataFile> {
        val nanoIdEntity = dataFileDelete.id.toEntity()

        val dataFileEntity =
            dataFileRepository.find("data_file_id", nanoIdEntity.value).firstResult<DataFileEntity>().awaitSuspending()
                ?: return Result.failure(DataFileException.NotFoundException(nanoIdEntity.value))

        dataFileEntity.deleted = true

        return try {
            Result.success(
                dataFileRepository.persist(dataFileEntity).awaitSuspending()
            )
        } catch (e: Exception) {
            Result.failure(RuntimeException("Could not delete data file in database"))
        }
    }

    @Transactional
    override suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFileOverview> {
        val dataFileEntity = DataFileEntity(dataFileCreate.name, dataFileCreate.extension, dataFileCreate.domain)

        return try {
            val result = Panache.withTransaction {
                dataFileRepository.persist(dataFileEntity).map {
                    it.toOverviewDTO()
                }
            }.awaitSuspending()
            Result.success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(RuntimeException("Could not save data file"))
        }
    }

    override suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>> {
        var sort: Sort? = null

        if (sortParams != null) {
            //Todo implement own sorting mechanism
            sort = Sort.by(sortParams.sortBy, Sort.Direction.valueOf(sortParams.direction))
        }

        var allDataFiles: PanacheQuery<DataFileOverviewDTO> = dataFileRepository.find(
            sort, searchParams
        ).project(
            DataFileOverviewDTO::class.java
        )

        val targetPage = allDataFiles.page<DataFileOverviewDTO>(Page.of(pagedParams.page, pagedParams.size))
        val pageCount = targetPage.pageCount().awaitSuspending()
        val content = targetPage.list<DataFileOverviewDTO>().awaitSuspending()

        val pagedList: PagedList<DataFileOverview> = PagedListImpl(
            pagedParams.page,
            pageCount,
            content
        )
        return Result.success(pagedList)
    }

    override suspend fun purge(dataFile: DataFile) {
        dataFileRepository.delete("data_file_id", dataFile.id!!.value).awaitSuspending()
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
