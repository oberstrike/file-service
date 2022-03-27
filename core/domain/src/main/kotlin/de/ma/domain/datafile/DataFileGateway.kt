package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam


//The DataFileGateway interface is the gateway to the data file domain.
interface DataFileGateway {
    suspend fun findById(nanoId: NanoId): Result<DataFileShow>

    suspend fun deleteById(nanoId: NanoId): Result<Unit>

    suspend fun save(dataFileCreate: DataFileCreate, folderId: NanoId): Result<DataFileShow>

    suspend fun findAll(
        pagedParams: PagedParams,
        searchParams: DataFileSearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<DataFileShow>>

    suspend fun recover(nanoId: NanoId): Boolean

    suspend fun purge(nanoId: NanoId): Boolean

    suspend fun exists(name: String, extension: String): Boolean

    suspend fun updateDataFile(dataFileUpdate: DataFileUpdate): Result<DataFileShow>

}
