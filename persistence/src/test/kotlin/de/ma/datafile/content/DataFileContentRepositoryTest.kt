package de.ma.datafile.content

import de.ma.domain.datafile.shared.NanoId
import de.ma.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import org.amshove.kluent.should
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject
import javax.persistence.EntityManager

@QuarkusTest
class DataFileContentRepositoryTest : AbstractDatabaseTest() {

    @Inject
    override lateinit var entityManager: EntityManager

    @Inject
    lateinit var dataFileContentRepository: DataFileContentRepository

    @Test
    fun findByNanoId() {
        val nanoId = object : NanoId {
            override val text: String
                get() = "Test"
        }
        val result = dataFileContentRepository.findByNanoId(nanoId)

        result shouldBe null
    }

    @Test
    fun save() {
        val nanoId = object : NanoId {
            override val text: String
                get() = "Tes2t"
        }
        //Create a InputStream from String "hello world"
        val content = "hello world2"
        val inputStream = content.byteInputStream()

        val size = dataFileContentRepository.save(nanoId, inputStream)

        //size should be equal to byte legnth of content
        size shouldBe content.length.toLong()

    }
}