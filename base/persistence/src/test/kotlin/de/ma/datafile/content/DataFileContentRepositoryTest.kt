package de.ma.datafile.content

import de.ma.content.DataFileContentRepository
import de.ma.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import javax.inject.Inject

@QuarkusTest
class DataFileContentRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileContentRepository: DataFileContentRepository

    @BeforeEach
    fun setup() {
        runBlocking {
            dataFileContentRepository.reset()
        }
    }

    @AfterEach
    fun tearDown() {
        runBlocking {
            dataFileContentRepository.reset()
        }
    }


    @Test
    fun `saves an file and check if its exists`() {
    }

    fun exists() {
    }
}