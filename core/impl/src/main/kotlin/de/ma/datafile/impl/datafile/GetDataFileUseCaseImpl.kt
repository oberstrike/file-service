package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.datafile.exceptions.DataFileException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class GetDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : GetDataFileUseCase {

    private val scope = Dispatchers.IO + Job()

    override suspend fun invoke(dataFileSearch: DataFileSearch): Result<DataFileShow> = withContext(scope) {
        val result = dataFileGateway.find(dataFileSearch)
            ?: return@withContext Result.failure(DataFileException.NotFoundException(dataFileSearch.id.value))
        return@withContext Result.success(result)
    }
}
