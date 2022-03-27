package de.ma.persistence.datafile.content.repository

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.persistence.datafile.content.repository.api.FindFileContent
import de.ma.persistence.datafile.content.data.DataFileContentShowDTO
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named("findFileContentByNanoId")
class FindFileContentImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val destinationFolder: String
) : FindFileContent {
    override suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow? {

        val filename = searchParams.id?.id?: return null
        //find the File recursively in the destinationFolder
        val file = File(destinationFolder).walkTopDown().find { it.name == filename } ?: return null

        return DataFileContentShowDTO(file, file.length())
    }
}
