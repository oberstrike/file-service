package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DataFileShow

suspend fun DataFileManagementUseCaseImpl.internalGetDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow> {

    val dataFileShow = dataFileGateway.findById(dataFileSearchParams.id).getOrElse {
        return Result.failure(
            it
        )
    }

    val dataFileContent = dataFileContentGateway.getContent(dataFileSearchParams).getOrElse {
            return Result.failure(
                it
            )
        }

    dataFileShow.content = dataFileContent
    return Result.success(dataFileShow)

}