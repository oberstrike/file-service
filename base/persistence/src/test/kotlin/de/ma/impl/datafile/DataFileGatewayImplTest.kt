package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileGateway
import de.ma.impl.utils.AbstractDatabaseTest
import de.ma.impl.utils.TransactionalQuarkusTest
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.should
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
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
    fun setup() = runBlocking {
        dataFileRepository.deleteAll().awaitSuspending()
        Unit
    }

    @Test
    fun `tests if the save action works`() = runBlocking {
        val input = "Mein Leben".byteInputStream()

        val contentCreate = dataFileContentCreate(input)
        val dataFileCreate = dataFileCreate("txt", "test", contentCreate)

        val result = dataFileGateway.save(dataFileCreate)

        result.isSuccess shouldBe true

        val all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()

        all.isEmpty() shouldBe false

        Unit
    }

    @Test
    fun `tests if the delete action works`() = runBlocking {

        val dataFileEntity = DataFileEntity("test", "txt")
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        var all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe false


        val deleted = dataFileGateway.delete(DataFileSearchDTO(result.id!!))

        deleted.isSuccess shouldBe true


        all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe false

        all.first().deleted shouldBe true


        Unit
    }


    @Test
    fun `Test if the find method works`() = runBlocking {

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
        Unit

    }

}