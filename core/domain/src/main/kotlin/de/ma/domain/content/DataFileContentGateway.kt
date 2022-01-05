package de.ma.domain.content

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DeleteParamsDataFile
import de.ma.domain.datafile.DataFileSearchParams

interface DataFileContentGateway {

    suspend fun getContent(dataFileSearchParams: DataFileSearchParams): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate, dataFileOverview: DataFileOverview): Result<DataFileContentOverview>

    suspend fun deleteContent(deleteParams: DeleteParamsDataFile): Result<Unit>

}
