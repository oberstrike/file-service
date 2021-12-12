package de.ma.utils


import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentShow
import de.ma.domain.datafile.shared.NanoId
import io.quarkus.test.common.QuarkusTestResource
import java.io.File
import java.io.InputStream
import java.util.*


@QuarkusTestResource(DatabaseTestResource::class)
abstract class AbstractDatabaseTest {

    data class NanoIdImpl(override val text: String) : NanoId

    data class DataFileContentCreateImpl(override val input: InputStream) : DataFileContentCreate

    fun nanoId(text: String = UUID.randomUUID().toString()): NanoId = NanoIdImpl(text)

    fun dataFileContentCreate(input: InputStream): DataFileContentCreate = DataFileContentCreateImpl(input)
}