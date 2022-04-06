package de.ma.domain.folder

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

interface FolderGateway {

    suspend fun oneFolderWithSameNameExists(name: String): Boolean

    suspend fun existsById(id: NanoId): Boolean

    suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow>

    suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean>

    suspend fun updateFolder(folderUpdate: FolderUpdate, folderId: NanoId): Result<FolderShow>

    suspend fun getFolderById(id: NanoId): Result<FolderShow>

    suspend fun getFoldersPaged(
        pagedParams: PagedParams? = null,
        folderSearchParams: FolderSearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<FolderShow>>

    suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit>

    suspend fun hasDataFileWithNameById(name: String, folderId: NanoId): Boolean

    suspend fun getDataFilesPaged(folderId: NanoId, pagedParams: PagedParams): Result<PagedList<DataFileShow>>


}