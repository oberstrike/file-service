package de.ma.datafile.api.management

import de.ma.domain.datafile.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

interface DataFileManagementUseCase {

    suspend fun dataFileCreate(createDataFile: DataFileCreate): Result<DataFileOverview>

    suspend fun deleteDataFile(dataFileDelete: DeleteParamsDataFile): Result<Unit>

    suspend fun getDataFile(dataFileSearchParams: de.ma.domain.datafile.DataFileSearchParams): Result<DataFileShow>

    suspend fun getDataFilesPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<DataFileOverview>>
}
