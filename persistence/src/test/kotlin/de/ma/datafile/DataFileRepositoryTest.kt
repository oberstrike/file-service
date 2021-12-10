package de.ma.datafile

import de.ma.utils.TransactionalQuarkusTest
import de.ma.utils.AbstractDatabaseTest
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.persistence.EntityManager


@TransactionalQuarkusTest
class DataFileRepositoryTest : AbstractDatabaseTest() {

    @Inject
    override lateinit var entityManager: EntityManager

    @Inject
    lateinit var dataFileRepository: DataFileRepository

    @BeforeEach
    fun setup() {
        entityManager.clear()
    }

    @Test
    fun test() {
        dataFileRepository.persist(DataFileEntity("Markus"))

        dataFileRepository.findAll().list<DataFileEntity>().size shouldBe 1
    }

}