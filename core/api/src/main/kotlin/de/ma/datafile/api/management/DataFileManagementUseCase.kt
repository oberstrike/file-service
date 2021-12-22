package de.ma.datafile.api.management

import de.ma.domain.datafile.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

interface DataFileManagementUseCase {
    suspend fun createDataFile(createDataFile: DataFileCreate): Result<DataFileOverview>

    suspend fun deleteDataFile(deleteDataFile: DataFileDelete): Result<Unit>

    suspend fun getDataFile(dataFileSearch: DataFileSearch): Result<DataFileShow>

    suspend fun getDataFiles(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<DataFileOverview>>
}
