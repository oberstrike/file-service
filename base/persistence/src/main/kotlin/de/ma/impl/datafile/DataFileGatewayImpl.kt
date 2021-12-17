package de.ma.impl.datafile

import de.ma.domain.datafile.*
import de.ma.impl.content.repository.DataFileContentRepositoryImpl
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import de.ma.impl.shared.pagedMap
import de.ma.impl.shared.toPagedList
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository,
    private val dataFileContentRepositoryImpl: DataFileContentRepositoryImpl
) : DataFileGateway {

    private val scope = Dispatchers.IO + Job()

    override suspend fun find(dataFileSearch: DataFileSearch): DataFileShow? {
        return withContext(scope) {
            val nanoId = dataFileSearch.id

            val dataFile = dataFileRepository.findById(nanoId).awaitSuspending() ?: return@withContext null

            val dataFileContentShow = dataFileContentRepositoryImpl.findByNanoId(nanoId) ?: return@withContext null

            return@withContext DataFileShowDTO(dataFileContentShow, dataFile.extension, dataFile.name)
        }
    }

    override suspend fun delete(dataFileDelete: DataFileSearch): Result<Boolean> = withContext(scope) {
        val nanoId = dataFileDelete.id
        val deleted = dataFileContentRepositoryImpl.deleteById(nanoId)
        if (deleted) {
            dataFileRepository.deleteById(nanoId).awaitSuspending()
            return@withContext Result.success(true)
        }

        //TODO implement error handling
        return@withContext Result.failure(RuntimeException("Could not delete data file"))
    }

    @Transactional
    override suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFileOverview> {
        val dataFileEntity = DataFileEntity(dataFileCreate.name, dataFileCreate.extension)
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        val dataFileContentOverview = dataFileContentRepositoryImpl.save(dataFileCreate.content, result.id!!)

        if (dataFileContentOverview != null) {
            result.size = dataFileContentOverview.size
            dataFileRepository.persist(result).awaitSuspending()

            return Result.success(result.toOverviewDTO())
        }

        // println("I'm deleting the wrong datafile")
        // deleteById(result.id!!)

        //TODO implement own exception
        return Result.failure(RuntimeException("Could not save data file"))
    }

    override suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>> {
        var sort: Sort? = null

        if (sortParams != null) {
            //Todo implement own sorting mechanism
            sort = Sort.by(sortParams.sortBy, Sort.Direction.Ascending)
        }

        val allDataFiles = if (sort == null) dataFileRepository.findAll() else dataFileRepository.findAll(sort)

        val pagedList: PagedList<DataFileEntity> = allDataFiles.toPagedList(pagedParams)

        val result = pagedList.pagedMap {
            it.toOverviewDTO()
        }

        return Result.success(result)
    }


}
