package de.ma.datafile.impl

import de.ma.datafile.api.content.DeleteDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.datafile.shared.NanoIdGateway

class DeleteDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : DeleteDataFileUseCase,
    BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    override fun<T: DataFileDelete> invoke(dataFile: T): Result<Boolean> {
        val dataFileShow = dataFileGateway.findById(dataFile.id.toNanoId())
            ?: return Result.failure(DataFileException.NotFoundException(dataFile.id))

        if (dataFile.version != dataFileShow.version) {
            return Result.failure(DataFileException.VersionException(dataFile.id, dataFile.version))
        }

        return Result.success(dataFileGateway.deleteById(dataFileShow.id.toNanoId()))
    }
}
