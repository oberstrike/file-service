package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams


//The DataFileGateway interface is the gateway to the data file domain.
interface DataFileGateway {
    suspend fun find(dataFileSearch: DataFileSearch): Result<DataFileShow>

    suspend fun delete(dataFileDelete: DataFileSearch): Result<DataFile>

    suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFileOverview>

    suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<DataFileOverview>>

    suspend fun recover(dataFile: DataFile)

    suspend fun purge(dataFile: DataFile)

}
