package de.ma.datafile

import de.ma.content.DataFileContentRepository
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository,
    private val dataFileContentRepository: DataFileContentRepository
) : DataFileGateway {

    private val scope = Dispatchers.IO + Job()

    override suspend fun findById(id: NanoId): DataFileShow? {
        return withContext(scope) {
            val dataFile = dataFileRepository.findById(id).awaitSuspending() ?: return@withContext null

            val dataFileContentShow = dataFileContentRepository.findByNanoId(id)?: return@withContext null

            return@withContext DataFileShowDTO(dataFileContentShow, dataFile.extension, dataFile.name)
        }
    }

    override fun deleteById(id: NanoId): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : DataFileCreate> save(dataFile: T): Result<DataFileOverview> {
        TODO("Not yet implemented")
    }

    override suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileOverview>> {
        TODO("Not yet implemented")
    }


}