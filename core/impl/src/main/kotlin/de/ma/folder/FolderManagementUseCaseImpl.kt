package de.ma.folder

import de.ma.datafile.api.management.DataFileCreateUseCase
import de.ma.datafile.api.management.FolderManagementUseCase
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

class FolderManagementUseCaseImpl(
    private val folderGateway: FolderGateway,
    private val dataFileCreateUseCase: DataFileCreateUseCase
) : FolderManagementUseCase {

    override suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit> {
        return folderGateway.deleteDatafilesFromFolder(id)
    }

    override suspend fun createDataFileInFolder(
        folderId: NanoId,
        dataFileCreate: DataFileCreate
    ): Result<NanoId> {

        val exists = folderGateway.existsById(folderId)

        if (!exists) {
            return Result.failure(RuntimeException("Folder with id ${folderId.id} does not exist"))
        }

        //check if the folder exists
        val folderHasDataFile = folderGateway.hasDataFileWithNameById(dataFileCreate.name, folderId)

        if (folderHasDataFile) {
            return Result.failure(RuntimeException("Folder already has a datafile with the name ${dataFileCreate.name}"))
        }

        return dataFileCreateUseCase.createDataFile(folderId, dataFileCreate)
    }

    override suspend fun getDataFilesFromFolder(
        id: NanoId,
        pagedParams: PagedParams
    ): Result<PagedList<DataFileShow>> {
        return folderGateway.getDataFilesPaged(
            folderId = id,
            pagedParams = pagedParams
        )
    }

    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow> {
        val exists = folderGateway.oneFolderWithSameNameExists(folderCreate.name)
        if (exists) {
            return Result.failure(RuntimeException("Folder with name ${folderCreate.name} already exists"))
        }

        return folderGateway.createFolder(folderCreate)
    }


    override suspend fun getFoldersPaged(
        pagedParams: PagedParams?,
        searchParams: FolderSearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderShow>> {
        return folderGateway.getFoldersPaged(pagedParams, searchParams, sortParams)
    }

    override suspend fun getFolderById(id: NanoId): Result<FolderShow> {
        return folderGateway.getFolderById(id)
    }

    override suspend fun deleteFolder(id: NanoId): Result<Boolean> {
        return deleteFolder(id)
    }

    override suspend fun updateFolder(folderUpdate: FolderUpdate, folderId: NanoId): Result<FolderShow> {
        return folderGateway.updateFolder(folderUpdate, folderId)
    }


}