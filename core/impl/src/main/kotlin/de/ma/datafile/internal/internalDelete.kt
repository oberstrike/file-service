package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DeleteParamsDataFile
import java.lang.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalDelete(dataFileDelete: DeleteParamsDataFile): Result<Unit>{
    //delete the datafile
    val dataFileDeleteResult = dataFileGateway.deleteById(dataFileDelete.id)

    val dataFile = dataFileDeleteResult.getOrNull()

    //if the datafile couldn't be deleted return Result a failure
    if (dataFileDeleteResult.isFailure || dataFile == null) {
        return Result.failure(
            dataFileDeleteResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
        )
    }

    //delete the datafile content
    val dataFileContentDeleteResult = dataFileContentGateway.deleteContent(dataFileDelete)

    //if the datafile content couldn't be deleted return Result a failure
    if (dataFileContentDeleteResult.isFailure) {
        dataFileGateway.recover(dataFile)

        return Result.failure(
            dataFileContentDeleteResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile content")
        )
    }

    dataFileGateway.purge(dataFile)
    return Result.success(Unit)
}