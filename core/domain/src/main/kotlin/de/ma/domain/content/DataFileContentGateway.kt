package de.ma.domain.content

interface DataFileContentGateway {

    suspend fun getContent(search: DataFileContentSearch): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate): Result<DataFileContentOverview>

    suspend fun deleteContent(search: DataFileContentSearch): Boolean
}
