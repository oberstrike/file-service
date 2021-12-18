package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.exceptions.DataFileException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CreateDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
) : CreateDataFileUseCase {

    private val scope = Dispatchers.IO + Job()

    //this use case has the task of creating a data file
    override suspend fun <T : DataFileCreate> invoke(dataFileCreate: T): Result<DataFileOverview> {

        //saves the data file ignore that the data file might already exist in the database
        val dataFile: Result<DataFileOverview> = dataFileGateway.save(dataFileCreate)

        //verify if the dataFile was saved
        val dataFileShow = dataFile.getOrNull()

        if (dataFile.isFailure || dataFileShow == null) {

            return Result.failure(
                DataFileException.InvalidDataFileException(
                    dataFile.exceptionOrNull()?.message
                        ?: "There was an error saving the data file"
                )
            )
        }

        return Result.success(dataFileShow)
    }


}
