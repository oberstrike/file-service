package de.ma.impl.datafile

import de.ma.domain.datafile.DataFile
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileSearch
import de.ma.impl.utils.AbstractDatabaseTest
import de.ma.impl.utils.TransactionalQuarkusTest
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import javax.inject.Inject

@TransactionalQuarkusTest
class DataFileGatewayImplTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileGateway: DataFileGateway

    @Inject
    lateinit var dataFileRepository: DataFileRepository


    @BeforeEach
    fun setup() = runTest() {
        dataFileRepository.deleteAll().awaitSuspending()
    }

    @Test
    fun `tests if the save action works`() = runTest {
        val input = "Mein Leben".byteInputStream()

        val contentCreate = dataFileContentCreate(input)
        val dataFileCreate = dataFileCreate("txt", "test", contentCreate)

        val result = dataFileGateway.save(dataFileCreate)

        result.isSuccess shouldBe true

        dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending().isEmpty() shouldBe false
    }

    @Test
    fun `tests if the delete action works`() = runTest {

        val dataFileEntity = DataFileEntity("test", "txt")
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        var all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe false


        val deleted = dataFileGateway.delete(DataFileSearchDTO(result.id!!))

        deleted.isSuccess shouldBe true


        all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe true


    }


    @Test
    fun `Test if the find method works`() = runTest {

        val persisted = dataFileRepository.persist(
            DataFileEntity(
                "test",
                "txt"
            )
        ).awaitSuspending()

        val dataFileSearch = dataFileSearch(
            persisted.id!!
        )

        val find = dataFileGateway.find(dataFileSearch)

        find shouldNotBe null
    }

}