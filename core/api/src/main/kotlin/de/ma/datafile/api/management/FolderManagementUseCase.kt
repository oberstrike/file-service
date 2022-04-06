package de.ma.datafile.api.management


import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

interface FolderManagementUseCase {

    //returns an overview of the folders paged according to the given parameters
    suspend fun getFoldersPaged(
        pagedParams: PagedParams? = null,
        searchParams: FolderSearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<FolderShow>>

    //returns the folder with content
    suspend fun getFolderById(id: NanoId): Result<FolderShow>

    //delete folder and children files
    suspend fun deleteFolder(id: NanoId): Result<Boolean>

    //updating a folder to rename it
    suspend fun updateFolder(folderUpdate: FolderUpdate, folderId: NanoId): Result<FolderShow>

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow>

    suspend fun createDataFileInFolder(folderId: NanoId, dataFileCreate: DataFileCreate): Result<NanoId>

    suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit>

    suspend fun getDataFilesFromFolder(id: NanoId, pagedParams: PagedParams): Result<PagedList<DataFileShow>>
}