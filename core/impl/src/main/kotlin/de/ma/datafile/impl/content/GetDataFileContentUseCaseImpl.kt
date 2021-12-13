package de.ma.datafile.impl.content

import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.nanoid.NanoId

class GetDataFileContentUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val dataFileContentGateway: DataFileContentGateway
) : GetDataFileContentUseCase {

    override suspend fun invoke(id: NanoId): Result<DataFileContentShow> {
        dataFileGateway.findById(id)
            ?: return Result.failure(DataFileException.NotFoundException(id.text))

        val content = dataFileContentGateway.getContentByNanoId(id)

        //TODO implement own exception
        val targetContent = content.getOrNull()
            ?: return Result.failure(RuntimeException(content.exceptionOrNull()?.message ?: "not found"))
        return Result.success(targetContent)
    }
}