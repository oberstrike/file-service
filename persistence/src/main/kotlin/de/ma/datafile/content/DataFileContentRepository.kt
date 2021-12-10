package de.ma.datafile.content

import de.ma.domain.datafile.shared.NanoId
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentRepository(
    @ConfigProperty(name = "datafile.content.folder")
    private val folderPath: String
) {

    private lateinit var folderAsFile: File

    @PostConstruct
    fun init() {
        val folder = File(folderPath)
        val isDirectory = folder.isDirectory
        if (isDirectory) {
            folderAsFile = folder
            return
        }

        val created = File(folderPath).mkdir()
        if (!created) {
            throw IllegalStateException("Could not create folder $folder")
        }

        folderAsFile = File(folderPath)

    }

    fun findByNanoId(nanoId: NanoId): File? {
        val file = File(folderAsFile, nanoId.toString())
        return if (file.exists()) file else null
    }

    fun save(nanoId: NanoId, content: InputStream): Long {
        val persistedFile = File(folderAsFile, nanoId.text)
        if (persistedFile.exists()) {
            throw IllegalStateException("File $persistedFile already exists")
        }

        try {
            Files.copy(content, persistedFile.toPath())
            return persistedFile.length()

        } catch (e: Exception) {
            throw IllegalStateException("Could not save file $persistedFile", e)
        }
    }

    fun exists(nanoId: NanoId): Boolean {
        val file = File(folderAsFile, nanoId.text)
        return file.exists() && !file.isDirectory
    }


}