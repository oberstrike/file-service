package de.ma.impl.content.repository

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.DataFileContentShowDTO
import de.ma.impl.content.repository.api.FindFileContentByNanoId
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named("findFileContentByNanoId")
class FindFileContentByNanoIdImpl(
    @ConfigProperty(name = "datafile.content.folder")
    private val domain: String
): FindFileContentByNanoId {
    override suspend fun findByNanoId(nanoId: NanoId): DataFileContentShow? {
        val file = File(domain, nanoId.value)
        if (!file.exists()) {
            return null
        }
        return DataFileContentShowDTO(file)
    }
}
