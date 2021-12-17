package de.ma.beans.datafile

import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.impl.datafile.CreateDataFileUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateDataFileUseCaseBean(
    private val dataFileGateway: DataFileGateway
) : CreateDataFileUseCase by CreateDataFileUseCaseImpl(dataFileGateway)