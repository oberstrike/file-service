package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoIdGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class DeleteDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val nanoIdGateway: NanoIdGateway
) : DeleteDataFileUseCase,
    BaseUseCase by BaseUseCaseImpl(nanoIdGateway) {

    private val scope = Dispatchers.IO + Job()

    override suspend fun<T: DataFileDelete> invoke(dataFile: T): Result<Boolean> = withContext(scope) {
        dataFileGateway.findById(dataFile.id.toNanoId())
            ?: return@withContext Result.failure(DataFileException.NotFoundException(dataFile.id))
        return@withContext Result.success(dataFileGateway.deleteById(dataFile.id.toNanoId()))
    }
}
