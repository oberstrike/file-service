package de.ma.impl.content.repository

import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

import javax.inject.Inject

@QuarkusTest
class DataFileContentRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileContentRepositoryImpl: de.ma.persistence.content.repository.DataFileContentRepositoryImpl

    @ConfigProperty(name = "datafile.content.folder")
    lateinit var domainPath: String

    @BeforeEach
    fun setup() {
        runBlocking {
            dataFileContentRepositoryImpl.reset()
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            dataFileContentRepositoryImpl.reset()
        }
    }


    @Test
    fun `saves an file and check if its exists`() = runBlocking(Dispatchers.IO) {

        val domain = File(domainPath)

        val nanoId = nanoId()

        //create a file with the nanoId value as name
        File(domain, nanoId.value).createNewFile()

        var files = domain.listFiles() ?: emptyArray()

        files.isNotEmpty() shouldBe true

        dataFileContentRepositoryImpl.reset()

        files = domain.listFiles() ?: emptyArray()
        files.isEmpty() shouldBe true
        Unit
    }


}
