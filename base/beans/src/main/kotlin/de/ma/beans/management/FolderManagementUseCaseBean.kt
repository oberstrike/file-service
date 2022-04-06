package de.ma.beans.management

import de.ma.datafile.api.management.DataFileCreateUseCase
import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.api.management.FolderManagementUseCase
import de.ma.domain.folder.FolderGateway
import de.ma.folder.FolderManagementUseCaseImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FolderManagementUseCaseBean(
    folderGateway: FolderGateway,
    dataFileCreateUseCase: DataFileCreateUseCase
) : FolderManagementUseCase by FolderManagementUseCaseImpl(folderGateway, dataFileCreateUseCase)