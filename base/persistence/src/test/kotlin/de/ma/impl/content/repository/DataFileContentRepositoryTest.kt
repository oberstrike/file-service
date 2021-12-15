package de.ma.impl.content.repository

import de.ma.impl.content.repository.DataFileContentRepositoryImpl
import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import javax.inject.Inject

@QuarkusTest
class DataFileContentRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileContentRepositoryImpl: DataFileContentRepositoryImpl

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
    fun `saves an file and check if its exists`() {
    }

    fun exists() {
    }
}
