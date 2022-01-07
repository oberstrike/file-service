package de.ma.persistence.content.repository

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.persistence.content.repository.api.FindFileContent
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.jetbrains.kotlin.util.prefixIfNot
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named("findFileContentByNanoId")
class FindFileContentImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val domain: String
) : FindFileContent {
    override suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow? {
        val targetDomain = searchParams.domain?.prefixIfNot("/") ?: ""

        val file = File(domain + targetDomain, searchParams.id.value)
        if (!file.exists()) {
            return null
        }
        return de.ma.persistence.content.DataFileContentShowDTO(file)
    }
}
