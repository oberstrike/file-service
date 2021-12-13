package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams

interface DataFileGateway {
     suspend fun findById(id: NanoId): DataFileShow?

    fun deleteById(id: NanoId): Boolean

     fun<T : DataFileCreate> save(dataFile: T): Result<DataFileOverview>

     suspend fun findAll(
         pagedParams: PagedParams,
         searchParams: SearchParams?
    ): Result<PagedList<DataFileOverview>>
}
