package de.ma.folder

import de.ma.datafile.api.management.FolderManagementUseCase
import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam

class FolderManagementUseCaseImpl(
    private val folderGateway: FolderGateway
) : FolderManagementUseCase {

    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow> {
        val oldFolder = folderGateway.getFolderByParams(folderCreate.toParams())

        if(oldFolder.isSuccess){
            return Result.failure(RuntimeException("A folder with the name: ${folderCreate.name} exists already"))
        }

        return folderGateway.createFolder(folderCreate)
    }

    override suspend fun getFoldersPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderOverview>> {
        return folderGateway.getFoldersPaged(pagedParams, searchParams, sortParams)
    }

    override suspend fun getFolderById(params: NanoId): Result<FolderShow> {
        return folderGateway.getFolderByParams(params)
    }

    override suspend fun deleteFolder(id: NanoId): Result<Boolean> {
        return with(folderGateway) {
            deleteFolder(id)
        }
    }

    override suspend fun updateFolder(folderUpdate: FolderUpdate): Result<FolderOverview> {
        return folderGateway.updateFolder(folderUpdate)
    }


}