package de.ma.impl.datafile

import de.ma.impl.content.DataFileContentRepository
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import de.ma.impl.nanoid.toDTO
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
    private val dataFileContentRepository: DataFileContentRepository
) : DataFileGateway {

    private val scope = Dispatchers.IO + Job()

    override suspend fun findById(id: NanoId): DataFileShow? {
        return withContext(scope) {
            val dataFile = dataFileRepository.findById(id).awaitSuspending() ?: return@withContext null

            val dataFileContentShow = dataFileContentRepository.findByNanoId(id) ?: return@withContext null

            return@withContext DataFileShowDTO(dataFileContentShow, dataFile.extension, dataFile.name)
        }
    }

    override suspend fun deleteById(id: NanoId): Boolean = withContext(scope) {
        val deleted = dataFileContentRepository.deleteById(id)
        if (deleted) {
            dataFileRepository.deleteById(id).awaitSuspending()
            return@withContext true
        }

        return@withContext false
    }

    @Transactional
    override suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFileOverview> {
        val dataFileEntity = DataFileEntity(dataFileCreate.name, dataFileCreate.extension)
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        val dataFileContentOverview = dataFileContentRepository.save(result.id!!.toDTO(), dataFileCreate.content)

        if (dataFileContentOverview != null) {
            return Result.success(result.toOverviewDTO())
        }
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