package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.nanoid.NanoIdGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class DeleteDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
) : DeleteDataFileUseCase {

    private val scope = Dispatchers.IO + Job()

    override suspend fun <T : DataFileSearch> invoke(dataFileDelete: T): Result<Boolean> {
        val dataFileDeletedResult = dataFileGateway.delete(dataFileDelete)

        if (dataFileDeletedResult.isFailure) {
            return Result.failure(dataFileDeletedResult.exceptionOrNull()!!)
        }
        return Result.success(dataFileDeletedResult.getOrNull()!!)
    }


}
