package de.ma.datafile.impl.content

import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.content.DataFileContentSearch
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.exceptions.DataFileException

class GetDataFileContentUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val dataFileContentGateway: DataFileContentGateway
) : GetDataFileContentUseCase {

    override suspend fun invoke(search: DataFileContentSearch): Result<DataFileContentShow> {
        val content = dataFileContentGateway.getContent(search)

        //TODO implement own exception
        val targetContent = content.getOrNull()
            ?: return Result.failure(DataFileException.NotFoundException(search.id.value))
        return Result.success(targetContent)
    }
}
