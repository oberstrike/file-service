package de.ma.datafile.impl.content

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId

class CreateDataFileContentUseCaseImpl(
    private val dataFileContentGateway: DataFileContentGateway
) : CreateDataFileContentUseCase {

    override suspend fun invoke(content: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview> {
        return dataFileContentGateway.saveContent(content)
    }

}
