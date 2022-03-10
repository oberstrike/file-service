package de.ma.persistence.datafile

import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.datafile.data.DataFileRepository
import de.ma.persistence.utils.TransactionalQuarkusTest
import de.ma.persistence.utils.AbstractDatabaseTest
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

    }

}