package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.GetDataFileByIdUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class GetDataFileByIdUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : GetDataFileByIdUseCase {

    private val scope = Dispatchers.IO + Job()


    override suspend fun invoke(id: NanoId): Result<DataFileShow> = withContext(scope) {
        val result = dataFileGateway.find(id)
            ?: return@withContext Result.failure(DataFileException.NotFoundException(id.value))
        return@withContext Result.success(result)
    }
}
