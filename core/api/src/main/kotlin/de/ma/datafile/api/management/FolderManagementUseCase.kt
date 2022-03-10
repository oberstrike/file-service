package de.ma.datafile.api.management

import de.ma.domain.folder.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

interface FolderManagementUseCase {

    //returns an overview of the folders paged according to the given parameters
    suspend fun getFoldersPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<FolderOverview>>

    //returns the folder with content
    suspend fun getFolder(params: FolderSearchParams): Result<FolderShow>

    //delete folder and children files
    suspend fun deleteFolder(params: FolderSearchParams): Result<Boolean>

    //updating a folder to rename it
    suspend fun updateFolder(folderUpdate: FolderUpdate): Result<FolderOverview>

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow>
}