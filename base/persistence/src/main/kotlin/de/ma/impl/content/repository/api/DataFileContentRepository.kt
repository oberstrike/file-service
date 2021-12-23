package de.ma.impl.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File

interface DataFileContentRepository {

    suspend fun findByNanoId(nanoId: NanoId): DataFileContentShow?

    suspend fun save(content: DataFileContentCreate, nanoId: NanoId): DataFileContentOverview?

    suspend fun reset()

    suspend fun deleteByNanoId(nanoId: NanoId): Boolean?

}
