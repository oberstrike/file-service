package de.ma.domain.content

import de.ma.domain.nanoid.NanoId

interface DataFileContentGateway {

    suspend fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow>

    suspend fun saveContentByNanoId(nanoId: NanoId, content: DataFileContentCreate): Result<DataFileContentOverview>

    suspend fun deleteContentByNanoId(nanoId: NanoId): Boolean
}