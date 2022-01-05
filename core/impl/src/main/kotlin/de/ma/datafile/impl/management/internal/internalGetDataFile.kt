package de.ma.datafile.impl.management.internal

import de.ma.datafile.impl.management.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.datafile.exceptions.DataFileException
import java.lang.RuntimeException

suspend fun DataFileManagementUseCaseImpl.internalGetDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow>{

    val dataFileShowResult = dataFileGateway.find(dataFileSearchParams)

    if (dataFileShowResult.isFailure) {
        return Result.failure(
            dataFileShowResult.exceptionOrNull()
                ?: DataFileException.NotFoundException(dataFileSearchParams.id.value)
        )
    }

    val dataFileShow = dataFileShowResult.getOrNull()!!

    val dataFileContentResult =
        dataFileContentGateway.getContent(dataFileSearchParams)

    if (dataFileContentResult.isFailure) {
        return Result.failure(
            dataFileContentResult.exceptionOrNull() ?: RuntimeException("Could not get datafile content")
        )
    }

    dataFileShow.content = dataFileContentResult.getOrNull()!!
    return Result.success(dataFileShow)

}