package de.ma.persistence.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams

interface DataFileContentRepository {

    suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow?

    suspend fun save(content: DataFileContentCreate, searchParams: DataFileSearchParams): DataFileContentOverview?

    suspend fun deleteAll()

    suspend fun delete(searchParams: DataFileSearchParams): Boolean?

}
