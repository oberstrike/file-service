package de.ma.datafile.impl.management.internal

import de.ma.datafile.impl.management.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DeleteParamsDataFile
import java.lang.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalDelete(dataFileDelete: DeleteParamsDataFile): Result<Unit>{
    //delete the datafile
    val dataFileDeleteResult = dataFileGateway.delete(dataFileDelete)

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