package de.ma.persistence.content.repository

import de.ma.persistence.datafile.content.repository.FindFileContentImpl
import de.ma.persistence.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

@QuarkusTest
class FindFileContentImplTest : AbstractDatabaseTest() {


    @Test
    fun `find FileContent successfully`(@TempDir dir: File) = runTest {
        val nanoId = nanoId()
        val domain = "domain"
        val searchParams = searchParams(nanoId, domain)

        val findFileContentByNanoIdImpl = FindFileContentImpl(dir.absolutePath)

        //create new file
        val parent = dir.absolutePath + "/${domain}"


        withContext(Dispatchers.IO) {
            File(parent).mkdirs()
            File(parent, nanoId.id).createNewFile()
        }

        val result = findFileContentByNanoIdImpl.find(searchParams)

        result shouldNotBe null
    }


    @Test
    fun `find FileContent fails`(@TempDir dir: File) = runTest {
        val nanoId = nanoId()
        val domain = "domain"
        val searchParams = searchParams(nanoId, domain)

        val findFileContentByNanoIdImpl = FindFileContentImpl(dir.absolutePath)

        val result = findFileContentByNanoIdImpl.find(searchParams)

        result shouldBe null
    }
}
