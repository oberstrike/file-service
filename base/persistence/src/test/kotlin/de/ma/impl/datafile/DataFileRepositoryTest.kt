package de.ma.impl.datafile

import de.ma.impl.utils.TransactionalQuarkusTest
import de.ma.impl.utils.AbstractDatabaseTest
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject


@TransactionalQuarkusTest
class DataFileRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileRepository: DataFileRepository


    @BeforeEach
    fun setup() = runBlocking {
        dataFileRepository.deleteAll().awaitSuspending()
        Unit
    }

    @Test
    fun `tests if persistence works`() = runTest {
        val persist = dataFileRepository.persist(DataFileEntity("Markus",  "txt")).awaitSuspending()
        persist.name shouldBe "Markus"

        val result = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        result.isNotEmpty() shouldBe true


        val dataFileEntity = result.first()
        dataFileEntity.name shouldBe "Markus"
        dataFileEntity.extension shouldBe "txt"

        println(dataFileEntity)
    }

}