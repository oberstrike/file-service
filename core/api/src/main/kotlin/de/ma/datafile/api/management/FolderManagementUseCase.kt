package de.ma.datafile.api.management


import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam

interface FolderManagementUseCase {

    //returns an overview of the folders paged according to the given parameters
    suspend fun getFoldersPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<FolderOverview>>

    //returns the folder with content
    suspend fun getFolderById(params: NanoId): Result<FolderShow>

    //delete folder and children files
    suspend fun deleteFolder(id: NanoId): Result<Boolean>

    //updating a folder to rename it
    suspend fun updateFolder(folderUpdate: FolderUpdate): Result<FolderOverview>

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow>

    suspend fun createDataFileInFolder(id: NanoId, dataFileCreateForm: DataFileCreate): Result<DataFileShow>

    suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit>

    suspend fun getDataFilesFromFolder(id: NanoId, limit: Int?, page: Int?): PagedList<DataFileShow>
}