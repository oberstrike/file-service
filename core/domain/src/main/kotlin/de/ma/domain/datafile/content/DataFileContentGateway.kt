package de.ma.domain.datafile.content

import de.ma.domain.datafile.shared.NanoId

interface DataFileContentGateway {

    suspend fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow>

    suspend fun saveContentByNanoId(nanoId: NanoId, content: DataFileContentCreate): Result<DataFileContentOverview>

    suspend fun deleteContentByNanoId(nanoId: NanoId): Boolean
}