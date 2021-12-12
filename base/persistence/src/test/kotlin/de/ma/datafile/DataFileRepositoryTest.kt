package de.ma.datafile

import de.ma.utils.TransactionalQuarkusTest
import de.ma.utils.AbstractDatabaseTest
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject


@TransactionalQuarkusTest
class DataFileRepositoryTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileRepository: DataFileRepository

    val scope = Dispatchers.IO

    @BeforeEach
    fun setup() = runBlocking {
        dataFileRepository.deleteAll().awaitSuspending()
        Unit
    }

    @Test
    fun test() = runBlocking {
        val persist = dataFileRepository.persist(DataFileEntity("Markus")).awaitSuspending()
        persist.name shouldBe "Markus"

        val atMost = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()

        println(atMost)

        Unit
    }

}