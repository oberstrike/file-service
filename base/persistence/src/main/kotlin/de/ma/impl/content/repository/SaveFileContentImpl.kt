package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.DataFileContentOverviewDTO
import de.ma.impl.content.repository.api.SaveFileContent
import java.io.File
import java.nio.file.Files

class SaveFileContentImpl(
    private val domain: String,
) : SaveFileContent {

    //saves the content to the file system synchron
    override suspend fun save(nanoId: NanoId, content: DataFileContentCreate): DataFileContentOverview? {
        val file = File(domain, nanoId.value)

        return try {
            Files.copy(content.input, file.toPath())
            DataFileContentOverviewDTO(file.length())
        } catch (e: Exception) {
            //TODO implement logging
            e.printStackTrace()
            null
        }

    }
}
