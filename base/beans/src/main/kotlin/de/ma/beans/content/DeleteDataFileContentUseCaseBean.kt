package de.ma.beans.content

import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.impl.content.DeleteDataFileContentUseCaseImpl
import de.ma.domain.content.DataFileContentGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteDataFileContentUseCaseBean(
    private val dataFileContentGateway: DataFileContentGateway
) : DeleteDataFileContentUseCase by DeleteDataFileContentUseCaseImpl(dataFileContentGateway)