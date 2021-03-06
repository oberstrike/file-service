package de.ma.persistence.datafile.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

interface SaveFileContent {

    suspend fun save(content: DataFileContentCreate, nanoId: NanoId): DataFileContentOverview?

}
