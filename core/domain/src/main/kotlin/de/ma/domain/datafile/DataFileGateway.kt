package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam


//The DataFileGateway interface is the gateway to the data file domain.
interface DataFileGateway {
    suspend fun findById(nanoId: NanoId): Result<DataFileShow>

    suspend fun deleteById(nanoId: NanoId): Result<DataFile>

    suspend fun <T : DataFileCreate> save(dataFileCreate: T): Result<DataFile>

    suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<DataFileShow>>

    suspend fun recover(dataFile: DataFile)

    suspend fun purge(dataFile: DataFile)

    suspend fun exists(name: String, extension: String): Boolean

}
