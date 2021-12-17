package de.ma.beans.datafile

import de.ma.datafile.api.datafile.GetDataFilesUseCase
import de.ma.datafile.impl.datafile.GetDataFilesUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GetDataFilesUseCaseBean(
    private val dataFileGateway: DataFileGateway
) : GetDataFilesUseCase by GetDataFilesUseCaseImpl(dataFileGateway)