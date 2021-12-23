package de.ma.beans.management

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.impl.management.DataFileManagementUseCaseImpl
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileManagementUseCaseBean(
    dataFileGateway: DataFileGateway,
    dataFileContentGateway: DataFileContentGateway
) : DataFileManagementUseCase by DataFileManagementUseCaseImpl(
    dataFileGateway,
    dataFileContentGateway
)