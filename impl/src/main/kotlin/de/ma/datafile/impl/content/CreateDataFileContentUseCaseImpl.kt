package de.ma.datafile.impl.content

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentGateway
import de.ma.domain.datafile.content.DataFileContentOverview
import de.ma.domain.datafile.shared.NanoId

class CreateDataFileContentUseCaseImpl(
    private val dataFileContentGateway: DataFileContentGateway
) : CreateDataFileContentUseCase {

    override fun invoke(content: DataFileContentCreate, id: NanoId): Result<DataFileContentOverview> {
        return dataFileContentGateway.saveContentByNanoId(id, content)
    }

}