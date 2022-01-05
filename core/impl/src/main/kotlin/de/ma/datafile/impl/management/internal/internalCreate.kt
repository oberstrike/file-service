package de.ma.datafile.impl.management.internal

import de.ma.datafile.impl.management.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileOverview
import java.lang.RuntimeException

internal suspend fun DataFileManagementUseCaseImpl.internalCreate(createDataFile: DataFileCreate) : Result<DataFileOverview> {
    //uses the createDataFileUseCase to create a datafile
    val dataFileOverviewResult = dataFileGateway.save(createDataFile)

    //if the datafile was not created
    if (dataFileOverviewResult.isFailure) {
        return Result.failure(
            dataFileOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile")
        )
    }

    //get the datafile overview
    val dataFileOverview = dataFileOverviewResult.getOrNull()!!

    val dataFileContentCreate = createDataFile.content

    //create the datafile content based on the datafile that was created above
    val dataFileContentOverviewResult = dataFileContentGateway.saveContent(dataFileContentCreate, dataFileOverview)

    //if data file content couldn't be created delete the datafile
    if (dataFileContentOverviewResult.isFailure) {
        val deleted = dataFileGateway.delete(dataFileOverview)

        //if the datafile couldn't be deleted return Result a failure
        val deletedValue = deleted.getOrNull()

        if (deleted.isFailure || deletedValue == null) {
            return Result.failure(
                dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
            )
        }

        dataFileGateway.purge(deletedValue)
        //if the datafile was deleted return Result a failure
        return Result.failure(
            dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile content")
        )
    }

    //if the datafile and the datafile content were created return Result a success
    return Result.success(dataFileOverview)}