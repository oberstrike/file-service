package de.ma.datafile.api.management

import de.ma.domain.datafile.*
import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam

interface DataFileManagementUseCase {

    suspend fun createDataFile(folderSearchParams: FolderSearchParams, createDataFile: DataFileCreate): Result<DataFileShow>

    suspend fun deleteDataFile(dataFileDelete: DeleteParamsDataFile): Result<Unit>

    suspend fun getDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow>

    suspend fun getDataFilesPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<DataFileShow>>
}
