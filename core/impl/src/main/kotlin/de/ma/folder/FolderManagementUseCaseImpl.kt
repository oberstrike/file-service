package de.ma.folder

import de.ma.datafile.api.management.DataFileManagementUseCase
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
    private val dataFileManagementUseCase: DataFileManagementUseCase
) : FolderManagementUseCase {

    override suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit> {
        return folderGateway.deleteDatafilesFromFolder(id)
    }

    override suspend fun createDataFileInFolder(
        folderId: NanoId,
        dataFileCreate: DataFileCreate
    ): Result<DataFileShow> {

        //check if the folder exists
        val folderHasDataFile = folderGateway.hasDataFileWithNameById(dataFileCreate.name, folderId)

        if(folderHasDataFile) {
            return Result.failure(RuntimeException("Folder already has a datafile with the name ${dataFileCreate.name}"))
        }


        //create the datafile
        val createdDataFileShow = dataFileManagementUseCase.createDataFile(
            folderId,
            dataFileCreate
        ).getOrElse {
            return Result.failure(it)
        }

        //add the data file to folder datafiles
        val isAdded = folderGateway.addDataFileToFolder(folderId, createdDataFileShow.id).getOrElse {
            return Result.failure(it)
        }

        if (!isAdded) {
            return Result.failure(RuntimeException("DataFile was not added to folder"))
        }

        return Result.success(createdDataFileShow)
    }

    override suspend fun getDataFilesFromFolder(id: NanoId,
                                                pagedParams: PagedParams): Result<PagedList<DataFileShow>> {
        return dataFileManagementUseCase.getDataFilesPaged(
            pagedParams = pagedParams
        )
    }

    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderOverview> {
        val exists = folderGateway.exists(folderCreate.name)
        if (exists) {
            return Result.failure(RuntimeException("Folder with name ${folderCreate.name} already exists"))
        }

        return folderGateway.createFolder(folderCreate)
    }


    override suspend fun getFoldersPaged(
        pagedParams: PagedParams?,
        searchParams: FolderSearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderOverview>> {
        return folderGateway.getFoldersPaged(pagedParams, searchParams, sortParams)
    }

    override suspend fun getFolderById(id: NanoId): Result<FolderShow> {
        return folderGateway.getFolderById(id)
    }

    override suspend fun deleteFolder(id: NanoId): Result<Boolean> {
        return deleteFolder(id)
    }

    override suspend fun updateFolder(folderUpdate: FolderUpdate, folderId: NanoId): Result<FolderOverview> {
        return folderGateway.updateFolder(folderUpdate, folderId)
    }


}