package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.datafile.exceptions.DataFileException

class GetDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : GetDataFileUseCase {


    override suspend fun invoke(dataFileSearch: DataFileSearch): Result<DataFileShow> {
        val result = dataFileGateway.find(dataFileSearch)
            ?: return Result.failure(DataFileException.NotFoundException(dataFileSearch.id.value))
        return Result.success(result)
    }
}
