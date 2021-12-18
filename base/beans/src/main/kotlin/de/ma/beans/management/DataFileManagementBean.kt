package de.ma.beans.management

import de.ma.beans.datafile.DeleteDataFileUseCaseBean
import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.datafile.api.datafile.GetDataFilesUseCase
import de.ma.datafile.api.management.DataFileManagement
import de.ma.datafile.impl.management.DataFileManagementImpl
import de.ma.domain.content.DataFileContentGateway
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileManagementBean(
    createDataFileContentUseCase: CreateDataFileContentUseCase,
    deleteDataFileContentUseCase: DeleteDataFileContentUseCase,
    getDataFileContentUseCase: GetDataFileContentUseCase,
    createDataFileUseCase: CreateDataFileUseCase,
    deleteDataFileUseCaseBean: DeleteDataFileUseCaseBean,
    getDataFilesUseCase: GetDataFilesUseCase,
    getDataFileUseCase: GetDataFileUseCase,
    dataFileContentGateway: DataFileContentGateway
) : DataFileManagement by DataFileManagementImpl(
    createDataFileContentUseCase,
    createDataFileUseCase,
    deleteDataFileUseCaseBean,
    deleteDataFileContentUseCase,
    getDataFileUseCase,
    getDataFileContentUseCase,
    getDataFilesUseCase,
    dataFileContentGateway
) {
}