package de.ma.domain.content

import de.ma.domain.nanoid.NanoId

interface DataFileContentGateway {

    suspend fun getContent(search: DataFileContentSearch): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview>

    suspend fun deleteContent(search: DataFileContentSearch): Result<Unit>
}
