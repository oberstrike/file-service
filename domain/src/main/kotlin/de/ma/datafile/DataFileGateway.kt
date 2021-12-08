package de.ma.datafile

import de.ma.shared.PagedList
import de.ma.shared.PagedParams
import de.ma.shared.SearchParams

interface DataFileGateway {
    fun findById(id: String): DataFileShowView?

    fun deleteById(id: String): Boolean

    fun save(dataFile: DataFileCreate): Result<DataFileShowView>

    fun findAll(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileShowView>>
}
