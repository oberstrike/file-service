package de.ma.datafile

import de.ma.datafile.exceptions.DataFileException

class SaveDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : SaveDataFileUseCase {

    override fun invoke(dataFile: DataFileCreate): Result<DataFileShowView> {
        val result: Result<DataFileShowView> = dataFileGateway.save(dataFile)

        if (result.isFailure) {
            return Result.failure(
                DataFileException.InvalidDataFileException(
                    result.exceptionOrNull()?.message
                        ?: "There was an error saving the data file"
                )
            )
        }

        return Result.success(result.getOrNull()!!)
    }
}
