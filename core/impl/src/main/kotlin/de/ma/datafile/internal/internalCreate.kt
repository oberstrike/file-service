package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import kotlin.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalCreate(createDataFile: DataFileCreate,
                                                                  folderId: NanoId): Result<DataFileShow> {

    //uses the createDataFileUseCase to create a datafile
    val dataFileShow = dataFileGateway.save(createDataFile, folderId).getOrElse {
        return Result.failure(it)
    }

    //save the content of the datafile
    dataFileContentGateway.saveContent(createDataFile.content, dataFileShow.id).getOrElse {
        //if the content could not be saved, delete the datafile
        dataFileGateway.purge(dataFileShow.id)
        return Result.failure(RuntimeException("Could not save content"))
    }

    //if the datafile and the datafile content were created return Result a success
    return Result.success(dataFileGateway.findById(dataFileShow.id).getOrNull()!!)
}