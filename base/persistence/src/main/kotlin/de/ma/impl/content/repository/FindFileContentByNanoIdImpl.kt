package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.DataFileContentShowDTO
import de.ma.impl.content.repository.api.FindFileContentByNanoId
import java.io.File

class FindFileContentByNanoIdImpl(
    private val domain: String
): FindFileContentByNanoId {
    override suspend fun findByNanoId(nanoId: NanoId): DataFileContentShow? {
        val file = File(domain, nanoId.value.toString())
        if (!file.exists()) {
            return null
        }
        return DataFileContentShowDTO(file)
    }
}
