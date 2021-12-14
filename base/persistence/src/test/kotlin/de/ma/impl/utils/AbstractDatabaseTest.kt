package de.ma.impl.utils


import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId
import io.quarkus.test.common.QuarkusTestResource
import java.io.InputStream
import java.util.*


@QuarkusTestResource(DatabaseTestResource::class)
abstract class AbstractDatabaseTest {

    data class NanoIdImpl(override var value: String) : NanoId

    data class DataFileContentCreateImpl(override val input: InputStream) : DataFileContentCreate

    data class DataFileCreateDTO(
        override val extension: String,
        override val name: String,
        override val content: DataFileContentCreate
    ) : DataFileCreate

    fun nanoId(text: String = UUID.randomUUID().toString()): NanoId = NanoIdImpl(text)

    fun dataFileContentCreate(input: InputStream): DataFileContentCreate = DataFileContentCreateImpl(input)

    fun dataFileCreate(extension: String, name: String, content: DataFileContentCreate): DataFileCreate =
        DataFileCreateDTO(
            extension,
            name,
            content
        )
}