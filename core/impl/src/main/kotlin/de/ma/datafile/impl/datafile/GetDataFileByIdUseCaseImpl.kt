package de.ma.datafile.impl.datafile

import de.ma.datafile.api.datafile.GetDataFileByIdUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoId

class GetDataFileByIdUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : GetDataFileByIdUseCase {
    override suspend fun invoke(id: NanoId): Result<DataFileOverview> {
        val result = dataFileGateway.findById(id)
            ?: return Result.failure(DataFileException.NotFoundException(id.text))
        return Result.success(result)
    }
}
