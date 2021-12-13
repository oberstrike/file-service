package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.ShowDataFilesUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class ShowDataFilesUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : ShowDataFilesUseCase {

    private val scope = Dispatchers.IO + Job()

    override suspend fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileOverview>> = withContext(scope) {
        return@withContext dataFileGateway.findAll(pagedParams, searchParams)
    }

}
