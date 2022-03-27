package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DeleteDataFileParams
import java.lang.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalDelete(dataFileDelete: DeleteDataFileParams): Result<Unit>{
    //delete the datafile
    val targetId = dataFileDelete.id

    val dataFileDeleteResult = dataFileGateway.deleteById(targetId)

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
        dataFileGateway.recover(targetId)

        return Result.failure(
            dataFileContentDeleteResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile content")
        )
    }

    dataFileGateway.purge(targetId)
    return Result.success(Unit)
}