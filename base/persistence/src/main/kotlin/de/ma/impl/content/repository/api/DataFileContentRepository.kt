package de.ma.impl.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File

interface DataFileContentRepository : SaveFileContent, FindFileContentByNanoId {

    fun exists(nanoId: NanoId): File?

    fun reset()

    fun deleteById(id: NanoId): Boolean
}
