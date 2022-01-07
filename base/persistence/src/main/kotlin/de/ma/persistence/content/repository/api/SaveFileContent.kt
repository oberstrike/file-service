package de.ma.persistence.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.datafile.DataFileSearchParams

interface SaveFileContent {

    suspend fun save(content: DataFileContentCreate, searchParams: DataFileSearchParams): DataFileContentOverview?

}
