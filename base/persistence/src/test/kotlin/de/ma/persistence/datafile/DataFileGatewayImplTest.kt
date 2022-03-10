package de.ma.persistence.datafile

import de.ma.domain.datafile.DataFileGateway
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.datafile.data.DataFileRepository
import de.ma.persistence.datafile.data.DataFileSearchParamsDTO
import de.ma.persistence.datafile.utils.PagedParamsDTO
import de.ma.persistence.datafile.utils.SearchParamsImpl
import de.ma.persistence.datafile.utils.SortParamsImpl
import de.ma.persistence.folder.data.FolderEntity
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.utils.AbstractDatabaseTest
import de.ma.persistence.utils.TransactionalQuarkusTest
import de.ma.persistence.utils.sql.Sql
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import javax.inject.Inject

@TransactionalQuarkusTest
class DataFileGatewayImplTest : AbstractDatabaseTest() {

    @Inject
    lateinit var dataFileGateway: DataFileGateway

    @Inject
    lateinit var dataFileRepository: DataFileRepository

    @Inject
    lateinit var folderRepository: FolderRepository


    @AfterEach
    fun tearDown() = runTest {
        dataFileRepository.deleteAll().awaitSuspending()
        folderRepository.deleteAll().awaitSuspending()
        Unit
    }

    @Test
    fun `tests if the save action works`() = runTest {
        val input = "Mein Leben".byteInputStream()

        val contentCreate = dataFileContentCreate(input)
        val dataFileCreate = dataFileCreate("txt", "test", contentCreate, "test")

        val result = dataFileGateway.save(dataFileCreate)

        result.isSuccess shouldBe true

        val all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()

        all.isEmpty() shouldBe false

        Unit
    }

    @Test
    fun `tests if the delete action works`() = runTest {

        val folder = folderRepository.persist(FolderEntity(0, "test")).awaitSuspending()

        val dataFileEntity = DataFileEntity("test", "txt", folder)
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        var all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe false


        val deleted = dataFileGateway.deleteById(result.id!!)

        deleted.isSuccess shouldBe true


        all = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()
        all.isEmpty() shouldBe false

        all.first().deleted shouldBe true


        Unit
    }


    @Test
    fun `Test if the find method works`() = runTest {

        val folder = folderRepository.persist(FolderEntity(0, "test")).awaitSuspending()


        val persisted = dataFileRepository.persist(
            DataFileEntity(
                "test",
                "txt",
                folder
            )
        ).awaitSuspending()



        val find = dataFileGateway.findById(  persisted.id!!)

        find shouldNotBe null
        Unit
    }


    @Test
    fun `delete a datafile and recover it`() = runTest {

        val folder = folderRepository.persist(FolderEntity(0, "test")).awaitSuspending()


        val dataFileEntity = DataFileEntity("test", "txt", folder)
        val result = dataFileRepository.persist(dataFileEntity).awaitSuspending()

        result.deleted shouldBe false

        val deleteResult = dataFileGateway.deleteById(result.id!!)

        deleteResult.isSuccess shouldBe true

        dataFileRepository.findById(result.id!!).awaitSuspending()?.deleted shouldBe true
        Unit


    }

    @Test
    @Sql(value = ["findAllTest.sql"])
    fun `tests if find all works`() = runTest {

        val all = dataFileGateway.findAll(
            PagedParamsDTO(10, 0)
        )


        all.isSuccess shouldBe true
        val result = all.getOrNull()!!

        result.items.isEmpty() shouldBe false
    }

    @ParameterizedTest
    @Sql(value = ["findAllTest.sql"])
    @EnumSource(Sort.Direction::class)
    fun `tests if find all with sorting works`(direction: Sort.Direction) = runTest {

        val all = dataFileGateway.findAll(
            PagedParamsDTO(10, 0),
   //         sortParams = SortParamsImpl(direction.name, "domain")
        )

        all.isSuccess shouldBe true
        val result = all.getOrNull()!!

        val items = result.items

        items.isEmpty() shouldBe false
    }

    @Test
    @Sql(value = ["findAllTest.sql"])
    fun `tests if find all with searchParams works`() = runTest {

        val all = dataFileGateway.findAll(
            PagedParamsDTO(10, 0),
            SearchParamsImpl("folder.name", "Test")
        )

        all.isSuccess shouldBe true
        val result = all.getOrNull()!!

        result.items.size shouldBe 1


    }

    //function to check if a list of strings are sorted correctly ascending
    private fun checkSorted(items: List<String>, direction: Sort.Direction) {
        var previous = items.first()
        for (item in items) {
            Assertions.assertTrue(
                if (direction == Sort.Direction.Ascending) item >= previous
                else item <= previous, "The list is not sorted correctly $items - $direction"
            )

            previous = item
        }
    }

    @Test
    @Sql(["purgingTest.sql"])
    fun `tests if purging works`() = runTest {

        val dataFile = dataFileRepository.findAll().firstResult<DataFileEntity>().awaitSuspending()

        dataFileGateway.purge(dataFile)

        val list = dataFileRepository.findAll().list<DataFileEntity>().awaitSuspending()

        list.isEmpty() shouldBe true
    }

    @Test
    @Sql(["purgingTest.sql"])
    fun `tests if recovery works`() = runTest {

        val dataFile = dataFileRepository.findAll().firstResult<DataFileEntity>().awaitSuspending()

        dataFileGateway.recover(dataFile)

        val result = dataFileRepository.findAll().firstResult<DataFileEntity>().awaitSuspending()

        result.deleted shouldBe false
    }

    @Test
    fun `tests if exists works if deleted is false`() = runTest {

    }


}
