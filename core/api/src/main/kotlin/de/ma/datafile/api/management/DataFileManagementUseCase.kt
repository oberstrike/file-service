package de.ma.datafile.api.management

import de.ma.domain.datafile.*
import de.ma.domain.folder.FolderOverview
import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.folder.FolderShow
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

interface DataFileManagementUseCase {

    suspend fun createDataFile(folderSearchParams: FolderSearchParams, createDataFile: DataFileCreate): Result<DataFileShow>

    suspend fun deleteDataFile(dataFileDelete: DeleteParamsDataFile): Result<Unit>

    suspend fun getDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow>

    suspend fun getDataFilesPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<DataFileShow>>
}
