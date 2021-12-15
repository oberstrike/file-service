package de.ma.impl.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId

interface SaveFileContent {

    suspend fun save(nanoId: NanoId, content: DataFileContentCreate): DataFileContentOverview?

}
