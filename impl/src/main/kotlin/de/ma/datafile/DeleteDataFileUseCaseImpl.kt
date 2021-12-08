package de.ma.datafile

import de.ma.datafile.exceptions.DataFileException

class DeleteDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : DeleteDataFileUseCase {

    override fun invoke(dataFile: DataFileDelete): Result<Boolean> {
        val dataFileShow = dataFileGateway.findById(dataFile.id)
            ?: return Result.failure(DataFileException.NotFoundException(dataFile.id))

        if (dataFile.version != dataFileShow.version) {
            return Result.failure(DataFileException.VersionException(dataFile.id, dataFile.version))
        }

        return Result.success(dataFileGateway.deleteById(dataFileShow.id))
    }
}
