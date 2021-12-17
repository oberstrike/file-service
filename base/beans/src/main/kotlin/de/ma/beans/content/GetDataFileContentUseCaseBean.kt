package de.ma.beans.content

import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.datafile.impl.content.GetDataFileContentUseCaseImpl
import de.ma.domain.content.DataFileContentGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GetDataFileContentUseCaseBean(
    val dataFileContentGateway: DataFileContentGateway
) : GetDataFileContentUseCase by GetDataFileContentUseCaseImpl(
    dataFileContentGateway
)