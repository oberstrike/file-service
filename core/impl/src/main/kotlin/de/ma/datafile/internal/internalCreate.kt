package de.ma.datafile.internal

import de.ma.datafile.DataFileCreateUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId
import kotlin.RuntimeException

internal suspend fun DataFileCreateUseCaseImpl.internalCreate(createDataFile: DataFileCreate,
                                                              folderId: NanoId): Result<NanoId> {

    //uses the createDataFileUseCase to create a datafile
    val dataFileNanoId = dataFileGateway.save(createDataFile, folderId).getOrElse {
        return Result.failure(it)
    }

    //save the content of the datafile
    dataFileContentGateway.saveContent(createDataFile.content, dataFileNanoId).getOrElse {
        //if the content could not be saved, delete the datafile
        dataFileGateway.purge(dataFileNanoId)
        return Result.failure(RuntimeException("Could not save content"))
    }

    //if the datafile and the datafile content were created return Result a success
    return Result.success(dataFileNanoId)
}