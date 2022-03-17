package de.ma.domain.folder

import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam

interface FolderGateway {

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow>

    suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean>

    suspend fun updateFolder(folderUpdate: FolderUpdate): Result<FolderOverview>

    suspend fun getFolderByParams(folderSearchParams: FolderSearchParams): Result<FolderShow>

    suspend fun getFoldersPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderOverview>>

}