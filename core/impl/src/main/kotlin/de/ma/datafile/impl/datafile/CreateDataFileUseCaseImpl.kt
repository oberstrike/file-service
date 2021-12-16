package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoIdGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class CreateDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : CreateDataFileUseCase,
    BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    private val scope = Dispatchers.IO + Job()

    //this use case has the task of creating a data file
    override suspend fun <T : DataFileCreate> invoke(dataFileCreate: T): Result<DataFileOverview> = withContext(scope) {

        //saves the data file ignore that the data file might already exist in the database
        val dataFile: Result<DataFileOverview> = dataFileGateway.save(dataFileCreate)

        //verify if the dataFile was saved
        val dataFileShow = dataFile.getOrNull()

        if (dataFile.isFailure || dataFileShow == null) {

            return@withContext Result.failure(
                DataFileException.InvalidDataFileException(
                    dataFile.exceptionOrNull()?.message
                        ?: "There was an error saving the data file"
                )
            )
        }

        return@withContext Result.success(dataFileShow)
    }


}
