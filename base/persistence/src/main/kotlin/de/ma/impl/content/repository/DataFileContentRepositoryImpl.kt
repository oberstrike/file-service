package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.repository.api.DataFileContentRepository
import de.ma.impl.content.repository.api.FindFileContentByNanoId
import de.ma.impl.content.repository.api.SaveFileContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentRepositoryImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val domainPath: String,
    private val saveFileContent: SaveFileContent,
    private val findFileContentByNanoId: FindFileContentByNanoId
) : DataFileContentRepository {

    private val scope = Dispatchers.IO + Job()

    private val jobs: MutableSet<Job> = Collections.synchronizedSet(mutableSetOf())

    private lateinit var domain: File


    @PreDestroy
    fun destroy() {
        jobs.forEach { it.cancel() }
    }

    @PostConstruct
    fun init() {
        domain = createFolder(domainPath) ?: throw IllegalStateException("Could not create domain folder")
    }

    override suspend fun findByNanoId(nanoId: NanoId): DataFileContentShow? {
        return withContext(scope) {
            return@withContext findFileContentByNanoId.findByNanoId(nanoId)
        }
    }

    //saves the content to the file system synchron
    override suspend fun save(content: DataFileContentCreate, nanoId: NanoId): DataFileContentOverview? =
        withContext(scope) {
            return@withContext saveFileContent.save(content, nanoId)
        }

    override suspend fun deleteByNanoId(nanoId: NanoId): Boolean? {
        val file = File(domain, nanoId.value)
        return if (file.exists()) file.delete() else false
    }

    override suspend fun reset() = withContext(scope) {
        domain.listFiles()?.forEach { file ->
            file.delete()
        }
        Unit
    }

    override suspend fun restoreByNanoId(nanoId: NanoId): Boolean? {
        TODO("Not yet implemented")
    }


    private fun createFolder(folderPath: String): File? {
        val folder = File(folderPath)
        val isDirectory = folder.isDirectory

        if (isDirectory) {
            return folder
        }

        val created = folder.mkdir()

        if (created) {
            return folder
        }

        return null
    }

}


