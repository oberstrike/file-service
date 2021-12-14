package de.ma.datafile.impl.datafile

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoIdGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class CreateDataFileUseCaseImpl(
    private val createDataFileContentUseCase: CreateDataFileContentUseCase,
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : CreateDataFileUseCase,
    BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    private val scope = Dispatchers.IO + Job()

    override suspend fun <T : DataFileCreate> invoke(dataFileCreate: T): Result<DataFileOverview> = withContext(scope) {
        val result: Result<DataFileOverview> = dataFileGateway.save(dataFileCreate)

        val dataFileShowView = result.getOrNull()

        if (result.isFailure || dataFileShowView == null) {
            return@withContext Result.failure(
                DataFileException.InvalidDataFileException(
                    result.exceptionOrNull()?.message
                        ?: "There was an error saving the data file"
                )
            )
        }

        createDataFileContentUseCase(dataFileCreate.content, dataFileShowView.id.toNanoId())

        return@withContext Result.success(dataFileShowView)
    }
}
