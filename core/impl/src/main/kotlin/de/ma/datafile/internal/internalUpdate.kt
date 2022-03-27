package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.datafile.DataFileUpdate

suspend fun DataFileManagementUseCaseImpl.internalUpdate(
    dataFileUpdate: DataFileUpdate
):  Result<DataFileShow>{
    return dataFileGateway.updateDataFile(dataFileUpdate)

}