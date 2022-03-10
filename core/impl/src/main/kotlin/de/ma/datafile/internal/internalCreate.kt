package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import kotlin.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalCreate(createDataFile: DataFileCreate): Result<DataFileShow> {

    //uses the createDataFileUseCase to create a datafile
    val dataFileShow = dataFileGateway.save(createDataFile).getOrElse {
        return Result.failure(it)
    }

    dataFileContentGateway.saveContent(createDataFile.content, dataFileShow.id!!).getOrElse {
        val deletedValue = dataFileGateway.deleteById(dataFileShow.id!!).getOrElse {
            return Result.failure(it)
        }
        dataFileGateway.purge(deletedValue)
        return Result.failure(RuntimeException("Could not save content"))
    }

    //if the datafile and the datafile content were created return Result a success
    return Result.success(dataFileGateway.findById(dataFileShow.id!!).getOrNull()!!)
}