package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileGateway
import de.ma.impl.utils.AbstractDatabaseTest
import de.ma.impl.utils.TransactionalQuarkusTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject

@TransactionalQuarkusTest
class DataFileGatewayImplTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileGateway: DataFileGateway

    @Test
    fun findById() {
    }

    @Test
    fun deleteById() {
    }

    @Test
    fun `tests if the save action works`() = runTest {
        val input = "Mein Leben".byteInputStream()

        val contentCreate = dataFileContentCreate(input)
        val dataFileCreate = dataFileCreate("txt", "test", contentCreate)

        val result = dataFileGateway.save(dataFileCreate)

        println(result)

    }

    @Test
    fun findAll() {
    }
}