package de.ma.impl.utils


import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.DataFileContentCreateDTO
import de.ma.impl.content.DataFileCreateDTO
import de.ma.impl.nanoid.NanoIdDTO
import io.quarkus.test.common.QuarkusTestResource
import java.io.InputStream
import java.util.*


@QuarkusTestResource(DatabaseTestResource::class)
abstract class AbstractDatabaseTest {

    fun withContentCreate(block: (DataFileContentCreate) -> Unit){
        return block(dataFileContentCreate(input()))
    }


    fun input(text: String = UUID.randomUUID().toString()): InputStream {
        return text.byteInputStream()
    }

    fun nanoId(text: String = UUID.randomUUID().toString()): NanoId = NanoIdDTO(text)

    fun dataFileContentCreate(input: InputStream): DataFileContentCreate {
        return DataFileContentCreateDTO(input)
    }

    fun dataFileCreate(extension: String, name: String, content: DataFileContentCreate): DataFileCreate =
        DataFileCreateDTO(
            content,
            extension,
            name
        )
}
