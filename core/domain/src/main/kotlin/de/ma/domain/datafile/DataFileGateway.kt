package de.ma.domain.datafile

import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams


//The DataFileGateway interface is the gateway to the data file domain.
interface DataFileGateway {
    suspend fun find(dataFileSearch: DataFileSearch): DataFileShow?

    suspend fun delete(dataFileDelete: DataFileSearch): Boolean

    suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFileOverview>

    suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>>
}
