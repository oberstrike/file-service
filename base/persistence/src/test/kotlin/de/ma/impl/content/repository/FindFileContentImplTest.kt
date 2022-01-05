package de.ma.impl.content.repository

import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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

        val findFileContentByNanoIdImpl = FindFileContentImpl(dir.absolutePath)

        //create new file
        withContext(Dispatchers.IO) {
            File(dir, nanoId.value).createNewFile()
        }


        val result = findFileContentByNanoIdImpl.find(nanoId)

        result shouldNotBe null
    }


    @Test
    fun `find FileContent fails`(@TempDir dir: File) = runTest {
        val nanoId = nanoId()

        val findFileContentByNanoIdImpl = FindFileContentImpl(dir.absolutePath)

        val result = findFileContentByNanoIdImpl.find(nanoId)

        result shouldBe null
    }
}