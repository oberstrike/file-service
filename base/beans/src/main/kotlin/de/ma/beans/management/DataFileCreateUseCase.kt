package de.ma.beans.management

import de.ma.datafile.DataFileCreateUseCaseImpl
import de.ma.datafile.api.management.DataFileCreateUseCase
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped


@ApplicationScoped
class DataFileCreateUseCaseBean(
    dataFileGateway: DataFileGateway,
    dataFileContentGateway: DataFileContentGateway
) : DataFileCreateUseCase by DataFileCreateUseCaseImpl(
    dataFileGateway = dataFileGateway,
    dataFileContentGateway = dataFileContentGateway
)
