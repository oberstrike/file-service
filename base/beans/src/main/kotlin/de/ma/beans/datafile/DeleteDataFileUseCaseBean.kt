package de.ma.beans.datafile

import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.impl.datafile.DeleteDataFileUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DeleteDataFileUseCaseBean(
    private val dataFileGateway: DataFileGateway
) : DeleteDataFileUseCase by DeleteDataFileUseCaseImpl(dataFileGateway)