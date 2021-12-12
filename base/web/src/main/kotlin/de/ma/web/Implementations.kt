package de.ma.web

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.impl.content.CreateDataFileContentUseCaseImpl
import de.ma.domain.datafile.content.DataFileContentGateway
import javax.enterprise.context.Dependent

@Dependent
class Implementations {


    @javax.enterprise.inject.Produces
    fun produces(dataFileContentGateway: DataFileContentGateway): CreateDataFileContentUseCase {
        return CreateDataFileContentUseCaseImpl(
            dataFileContentGateway
        )
    }


}