package de.ma.domain.content

import de.ma.domain.datafile.DataFile
import de.ma.domain.datafile.DeleteParamsDataFile
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

interface DataFileContentGateway {

    suspend fun getContent(dataFileSearchParams: DataFileSearchParams): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview>

    suspend fun deleteContent(deleteParams: DeleteParamsDataFile): Result<Unit>

}
