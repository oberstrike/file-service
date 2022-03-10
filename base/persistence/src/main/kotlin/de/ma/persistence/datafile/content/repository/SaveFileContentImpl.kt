package de.ma.persistence.datafile.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.content.data.DataFileContentOverviewDTO
import de.ma.persistence.datafile.content.repository.api.SaveFileContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
@ApplicationScoped
@Named(value = "saveFileContent")
class SaveFileContentImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val destinationFolder: String,
    @ConfigProperty(name = "datafile.content.max.size", defaultValue = "10MB")
    private val maxSize: String
) : SaveFileContent {

    private val _maxSize: Long = if(isSize(maxSize)) convertSize(maxSize) else convertSize("10GB")

    //checks a string if it is "%dMB", "%dKB" or "%dGB"
    private fun isSize(size: String?): Boolean {
        contract {
            returns(true) implies (size is String)
        }

        if(size == null) return false
        //contracts that size is string not null

        return size.matches(Regex("""\d+[KMG]B"""))
    }

    //a method that converts a string "%dMB" to a long, "%dKB" to a long and "%dGB" to a long
    private fun convertSize(size: String): Long {
        val unit = size.substring(size.length - 2, size.length)
        val value = size.substring(0, size.length - 2)
        return when (unit) {
            "MB" -> value.toLong() * 1024 * 1024
            "KB" -> value.toLong() * 1024
            "GB" -> value.toLong() * 1024 * 1024 * 1024
            else -> 0
        }
    }


    //saves the content to the file system synchron
    override suspend fun save(content: DataFileContentCreate, nanoId: NanoId): DataFileContentOverview? {

        val targetUrl = destinationFolder

        if (!Files.exists(File(targetUrl).toPath())) {
            withContext(Dispatchers.IO) {
                Files.createDirectories(File(targetUrl).toPath())
            }
        }

        var directory = File(targetUrl, nanoId.id)

        //check the size of the directory file if so create a subfolder
        if (directory.length() > _maxSize) {
            directory = File(directory.parent, nanoId.id)
            withContext(Dispatchers.IO) {
                Files.createDirectories(directory.toPath())
            }
        }

        return try {
            withContext(Dispatchers.IO) {
                Files.copy(content.input, directory.toPath())
            }
            DataFileContentOverviewDTO(directory.length())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}
