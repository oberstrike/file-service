package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.repository.api.DataFileContentRepository
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
    private val domainPath: String = "/home/oberstrike/Desktop/content"
) : DataFileContentRepository {

    private val scope = Dispatchers.IO + Job()

    private val jobs: MutableSet<Job> = Collections.synchronizedSet(mutableSetOf())

    private lateinit var domain: File

    private val saveFileContent = SaveFileContentImpl(domainPath)

    private val findFileContentByNanoId = FindFileContentByNanoIdImpl(domainPath)


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

    override fun exists(nanoId: NanoId): File? {
        val file = File(domain, nanoId.value)
        return if (file.exists()) file else null
    }

    //resets the domain
    override fun reset() {
        domain.listFiles()?.forEach { file ->
            file.delete()
        }
    }


    override fun deleteById(id: NanoId): Boolean {
        val file = File(domain, id.toString())
        return file.delete()
    }


}


