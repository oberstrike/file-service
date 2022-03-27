package de.ma.persistence.datafile

import de.ma.domain.datafile.DataFileGateway
import de.ma.persistence.datafile.data.DataFileRepository
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.utils.AbstractDatabaseTest
import de.ma.persistence.utils.TransactionalQuarkusTest
import de.ma.persistence.utils.sql.Sql
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
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

    }

    @Test
    fun `tests if the delete action works`() = runTest {

    }


    @Test
    fun `Test if the find method works`() = runTest {

    }


    @Test
    fun `delete a datafile and recover it`() = runTest {



    }

    @Test
    @Sql(before = ["findAllTest.sql"])
    fun `tests if find all works`() = runTest {

    }

    @ParameterizedTest
    @Sql(before = ["findAllTest.sql"])
    @EnumSource(Sort.Direction::class)
    fun `tests if find all with sorting works`(direction: Sort.Direction) = runTest {

    }

    @Test
    @Sql(before = ["findAllTest.sql"])
    fun `tests if find all with searchParams works`() = runTest {





    }

    @Test
    @Sql(before = ["purgingTest.sql"])
    fun `tests if purging works`() = runTest {


    }

    @Test
    @Sql(before = ["purgingTest.sql"])
    fun `tests if recovery works`() = runTest {

    }

    @Test
    fun `tests if exists works if deleted is false`() = runTest {

    }


}
