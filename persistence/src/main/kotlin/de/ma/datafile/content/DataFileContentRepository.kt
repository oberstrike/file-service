package de.ma.datafile.content

import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentOverview
import de.ma.domain.datafile.shared.NanoId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.nio.file.Files
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentRepository(
    @ConfigProperty(name = "datafile.content.folder")
    private val domainPath: String
) {

    private lateinit var domain: File

    private val jobs: MutableSet<Job> = Collections.synchronizedSet(mutableSetOf())

    private val scope = Dispatchers.IO + Job()

    @PreDestroy
    fun destroy() {
        jobs.forEach { it.cancel() }
    }

    @PostConstruct
    fun init() {
        val folder = File(domainPath)
        val isDirectory = folder.isDirectory
        if (isDirectory) {
            domain = folder
            return
        }

        val created = File(domainPath).mkdir()
        if (!created) {
            throw IllegalStateException("Could not create folder $folder")
        }

        domain = File(domainPath)

    }

    suspend fun findByNanoId(nanoId: NanoId): File? {
        return withContext(scope) {
            val file = File(domain, nanoId.toString())
            if (!file.exists()) {
                return@withContext null
            }
            return@withContext file
        }
    }

    //saves the content to the file system synchron
    suspend fun save(nanoId: NanoId, content: DataFileContentCreate): Long? = withContext(scope) {
        val file = File(domain, nanoId.toString())

        return@withContext try {
            Files.copy(content.input, file.toPath())
            content.input.available().toLong()
        } catch (e: Exception) {
            //TODO implement logging
            e.printStackTrace()
            0L
        }
    }

    fun exists(nanoId: NanoId): File? {
        val file = File(domain, nanoId.text)
        return if (file.exists()) file else null
    }

    //resets the domain
    fun reset() {
        domain.listFiles()?.forEach { file ->
            file.delete()
        }
    }


}


data class DataFileContentOverviewDTO(override val size: Long) : DataFileContentOverview