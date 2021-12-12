package de.ma.datafile.impl

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.CreateDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShowView
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.datafile.shared.NanoIdGateway

class CreateDataFileUseCaseImpl(
    private val createDataFileContentUseCase: CreateDataFileContentUseCase,
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : CreateDataFileUseCase, BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    override suspend fun invoke(dataFileCreate: DataFileCreate): Result<DataFileShowView> {
        val result: Result<DataFileShowView> = dataFileGateway.save(dataFileCreate)

        val dataFileShowView = result.getOrNull()

        if (result.isFailure || dataFileShowView == null) {
            return Result.failure(
                DataFileException.InvalidDataFileException(
                    result.exceptionOrNull()?.message
                        ?: "There was an error saving the data file"
                )
            )
        }

        createDataFileContentUseCase(dataFileCreate.content, dataFileShowView.id.toNanoId())

        return Result.success(dataFileShowView)
    }
}
