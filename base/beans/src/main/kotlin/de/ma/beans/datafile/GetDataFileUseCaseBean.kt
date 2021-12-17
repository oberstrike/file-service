package de.ma.beans.datafile

import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.datafile.impl.datafile.GetDataFileUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GetDataFileUseCaseBean(
    private val dataFileGateway: DataFileGateway
) : GetDataFileUseCase by GetDataFileUseCaseImpl(dataFileGateway)