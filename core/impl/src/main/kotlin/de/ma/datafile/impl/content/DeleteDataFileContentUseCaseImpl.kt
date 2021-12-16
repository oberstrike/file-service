package de.ma.datafile.impl.content

import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.impl.shared.BaseUseCase
import de.ma.datafile.impl.shared.BaseUseCaseImpl
import de.ma.domain.content.DataFileContentDelete
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.nanoid.NanoIdGateway

class DeleteDataFileContentUseCaseImpl(
    private val dataFileContentGateway: DataFileContentGateway,
    private val nanoIdGateway: NanoIdGateway
) : BaseUseCase by BaseUseCaseImpl(nanoIdGateway),
    DeleteDataFileContentUseCase {

    override suspend fun invoke(dataFileDelete: DataFileContentDelete): Result<Unit> {
        return dataFileContentGateway.deleteContent(dataFileDelete)
    }

}