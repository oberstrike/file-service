package de.ma.domain.content

import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.nanoid.NanoId

interface DataFileContentGateway {

    suspend fun getContent(search: DataFileContentSearch): Result<DataFileContentShow>

    suspend fun saveContent(contentCreate: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview>

    suspend fun deleteContent(search: DataFileContentSearch): Result<Unit>

    fun toContentSearch(dataFileSearch: DataFileSearch): DataFileContentSearch

    fun toContentDelete(deleteDataFile: DataFileDelete): DataFileContentDelete

}
