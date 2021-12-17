package de.ma.datafile.impl.management

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.api.management.DataFileManagement
import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.content.DataFileContentDelete
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.nanoid.NanoIdGateway
import java.lang.RuntimeException

class DataFileManagementImpl(
    private val createDataFileContentUseCase: CreateDataFileContentUseCase,
    private val createDataFileUseCase: CreateDataFileUseCase,
    private val deleteDataFileUseCase: DeleteDataFileUseCase,
    private val deleteDataFileContentUseCase: DeleteDataFileContentUseCase,
    private val nanoIdGateway: NanoIdGateway
) : DataFileManagement, BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {


    //create Data File whole process
    override suspend fun createDataFile(createDataFile: DataFileCreate): Result<Unit> {

        //uses the createDataFileUseCase to create a datafile
        val dataFileOverviewResult = createDataFileUseCase(createDataFile)

        //if the datafile was not created
        if (dataFileOverviewResult.isFailure) {
            return Result.failure(
                dataFileOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile")
            )
        }

        //get the datafile overview
        val dataFileOverview = dataFileOverviewResult.getOrNull()!!

        //prepare dataFileContent
        val nanoId = dataFileOverview.id

        val dataFileContentCreate = createDataFile.content

        //create the datafile content
        val dataFileContentOverviewResult = createDataFileContentUseCase(dataFileContentCreate, nanoId)

        //if data file content couldn't be created delete the datafile
        if (dataFileContentOverviewResult.isFailure) {
            val deleted = deleteDataFileUseCase(dataFileOverview)

            //if the datafile couldn't be deleted return Result a failure
            if (deleted.isFailure || deleted.getOrNull() == false) {
                return Result.failure(
                    dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
                )
            }

            //if the datafile was deleted return Result a failure
            return Result.failure(
                dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile content")
            )
        }

        //if the datafile and the datafile content were created return Result a success
        return Result.success(Unit)
    }

    /*
        delete data file and data file content
     */
    override suspend fun deleteDataFile(deleteDataFile: DataFileDelete): Result<Unit> {

        //first delete the datafile content
        val dataFileContentDeleted = deleteDataFileContentUseCase(deleteDataFile as DataFileContentDelete)

        //if the datafile content couldn't be deleted
        if (dataFileContentDeleted.isFailure) {
            return Result.failure(
                dataFileContentDeleted.exceptionOrNull() ?: RuntimeException("Could not delete datafile content")
            )
        }

        //then delete the datafile
        val dataFileDeleted = deleteDataFileUseCase(deleteDataFile)

        if (dataFileDeleted.isFailure) {
            return Result.failure(
                dataFileDeleted.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
            )
        }
        return Result.success(Unit)
    }


}