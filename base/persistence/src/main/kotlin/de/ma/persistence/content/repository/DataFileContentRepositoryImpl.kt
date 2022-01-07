package de.ma.persistence.content.repository

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.persistence.content.repository.api.DataFileContentRepository
import de.ma.persistence.content.repository.api.FindFileContent
import de.ma.persistence.content.repository.api.SaveFileContent
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
    private val findFileContent: FindFileContent
) : DataFileContentRepository {

    private val scope = Dispatchers.IO + Job()

    private val jobs: MutableSet<Job> = Collections.synchronizedSet(mutableSetOf())

    private lateinit var baseFolder: File


    @PreDestroy
    fun destroy() {
        jobs.forEach { it.cancel() }
    }

    @PostConstruct
    fun init() {
        baseFolder = createFolder(domainPath) ?: throw IllegalStateException("Could not create domain folder")
    }

    override suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow? {
        return withContext(scope) {
            return@withContext findFileContent.find(searchParams)
        }
    }

    //saves the content to the file system synchron
    override suspend fun save(
        content: DataFileContentCreate,
        searchParams: DataFileSearchParams
    ): DataFileContentOverview? =
        withContext(scope) {
            return@withContext saveFileContent.save(content, searchParams)
        }

    override suspend fun deleteByNanoId(searchParams: DataFileSearchParams): Boolean? {
        val file = File(baseFolder, searchParams.id.value)
        return if (file.exists()) file.delete() else false
    }

    override suspend fun reset() = withContext(scope) {
        baseFolder.listFiles()?.forEach { file ->
            file.delete()
        }
        Unit
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


