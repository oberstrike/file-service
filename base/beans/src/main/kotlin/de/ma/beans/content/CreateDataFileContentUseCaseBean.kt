package de.ma.beans.content

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.impl.content.CreateDataFileContentUseCaseImpl
import de.ma.domain.content.DataFileContentGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateDataFileContentUseCaseBean(
    private val dataFileContentGateway: DataFileContentGateway
) : CreateDataFileContentUseCase by CreateDataFileContentUseCaseImpl(dataFileContentGateway)