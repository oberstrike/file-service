package de.ma.impl.content.repository.api

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId

interface FindFileContentByNanoId {
    suspend fun findByNanoId(nanoId: NanoId): DataFileContentShow?

}
