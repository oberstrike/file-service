package de.ma.domain.content

import de.ma.domain.datafile.DeleteDataFileParams
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

interface DataFileContentGateway {

    suspend fun getContent(dataFileSearchParams: DataFileSearchParams): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview>

    suspend fun deleteContent(deleteParams: DeleteDataFileParams): Result<Unit>

}
