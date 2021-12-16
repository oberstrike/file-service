package de.ma.datafile.impl.datafile

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoId
import de.ma.domain.nanoid.NanoIdGateway
import jdk.internal.org.jline.utils.Colors.s
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class CreateDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : CreateDataFileUseCase,
    BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    private val scope = Dispatchers.IO + Job()

    override suspend fun <T : DataFileCreate> invoke(dataFileCreate: T): Result<DataFileOverview> = withContext(scope) {

        //if the content was saved, create the datafile
        val dataFile: Result<DataFileOverview> = dataFileGateway.save(dataFileCreate)

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
