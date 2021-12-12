package de.ma.domain.datafile

import de.ma.domain.datafile.shared.NanoId
import de.ma.domain.datafile.shared.PagedList
import de.ma.domain.datafile.shared.PagedParams
import de.ma.domain.datafile.shared.SearchParams

interface DataFileGateway {
     fun findById(id: NanoId): DataFileShowView?

     fun deleteById(id: NanoId): Boolean

     fun save(dataFile: DataFileCreate): Result<DataFileShowView>

     fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileShowView>>
}
