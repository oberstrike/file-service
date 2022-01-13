package de.ma.persistence.utils


import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.utils.DataFileCreateDTO
import de.ma.persistence.datafile.DataFileSearchParamsDTO
import de.ma.persistence.nanoid.NanoIdDTO
import io.quarkus.test.common.QuarkusTestResource
import java.io.InputStream
import java.util.*


@QuarkusTestResource(DatabaseTestResource::class)
abstract class AbstractDatabaseTest {

    fun withContentCreate(text: String = UUID.randomUUID().toString(), block: (DataFileContentCreate) -> Unit) {
        return block(dataFileContentCreate(input(text)))
    }

    fun input(text: String = UUID.randomUUID().toString()): InputStream {
        return text.byteInputStream()
    }

    fun searchParams(nanoId: NanoId, domain: String?): DataFileSearchParamsDTO {
        return DataFileSearchParamsDTO(
            nanoId,
            domain
        )
    }

    fun nanoId(text: String = UUID.randomUUID().toString()): NanoId = NanoIdDTO(text)

    fun dataFileContentCreate(input: InputStream): DataFileContentCreate {
        return de.ma.persistence.content.DataFileContentCreateDTO(input)
    }

    fun dataFileSearch(
        nanoId: NanoId = nanoId(),
        domain: String? = null
    ): DataFileSearchParamsDTO {
        return DataFileSearchParamsDTO(
            nanoId,
            domain
        )
    }


    fun dataFileCreate(
        extension: String,
        name: String,
        content: DataFileContentCreate,
        domain: String
    ): DataFileCreate =
        DataFileCreateDTO(
            content,
            extension,
            name,
            domain
        )
}