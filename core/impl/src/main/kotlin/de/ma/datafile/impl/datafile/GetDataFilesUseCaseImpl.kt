package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.GetDataFilesUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class GetDataFilesUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : GetDataFilesUseCase {

    private val scope = Dispatchers.IO + Job()

    override suspend fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>> {
        return dataFileGateway.findAll(pagedParams, searchParams, sortParams)
    }

}
