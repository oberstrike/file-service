package de.ma.beans

import de.ma.datafile.api.datafile.ShowDataFilesUseCase
import de.ma.datafile.impl.datafile.ShowDataFilesUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ShowDataFilesUseCaseBean(
    private val dataFileGateway: DataFileGateway
) : ShowDataFilesUseCase by ShowDataFilesUseCaseImpl(dataFileGateway)