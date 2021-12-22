package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.DataFileContentOverviewDTO
import de.ma.impl.content.repository.api.SaveFileContent
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named(value = "saveFileContent")
class SaveFileContentImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val domain: String,
) : SaveFileContent {

    //saves the content to the file system synchron
    override suspend fun save(content: DataFileContentCreate,
                              nanoId: NanoId): DataFileContentOverview? {
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
