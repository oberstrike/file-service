package de.ma.domain.folder

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

interface FolderGateway {

    suspend fun exists(name: String): Boolean

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderOverview>

    suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean>

    suspend fun updateFolder(folderUpdate: FolderUpdate, folderId: NanoId): Result<FolderOverview>

    suspend fun getFolderById(id: NanoId): Result<FolderShow>

    suspend fun getFoldersPaged(
        pagedParams: PagedParams? = null,
        folderSearchParams: FolderSearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<FolderOverview>>

    suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit>

   suspend fun addDataFileToFolder(folderId: NanoId, dataFileId: NanoId): Result<Boolean>

   suspend fun hasDataFileWithNameById(name: String, folderId: NanoId): Boolean

}