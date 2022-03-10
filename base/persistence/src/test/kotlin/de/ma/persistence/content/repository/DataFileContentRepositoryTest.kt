package de.ma.persistence.content.repository

import de.ma.persistence.datafile.content.repository.DataFileContentRepositoryImpl
import de.ma.persistence.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

import javax.inject.Inject

@QuarkusTest
class DataFileContentRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileContentRepositoryImpl: DataFileContentRepositoryImpl

    @ConfigProperty(name = "datafile.content.folder")
    lateinit var domainPath: String

    @BeforeEach
    fun setup() {
        runBlocking {
            dataFileContentRepositoryImpl.deleteAll()
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            dataFileContentRepositoryImpl.deleteAll()
        }
    }


    @Test
    fun `saves an file and check if its exists`() = runTest {

        val domain = File(domainPath)

        val nanoId = nanoId()

        //create a file with the nanoId value as name
        File(domain, nanoId.id).createNewFile()

        var files = domain.listFiles() ?: emptyArray()

        files.isNotEmpty() shouldBe true

        dataFileContentRepositoryImpl.deleteAll()

        files = domain.listFiles() ?: emptyArray()
        files.isEmpty() shouldBe true

    }


}
