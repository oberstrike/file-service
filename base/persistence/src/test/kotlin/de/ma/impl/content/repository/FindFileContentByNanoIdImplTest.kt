package de.ma.impl.content.repository

import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.io.TempDir
import java.io.File

@QuarkusTest
class FindFileContentByNanoIdImplTest : AbstractDatabaseTest() {


    @Test
    fun `find FileContent successfully`(@TempDir dir: File) = runTest {
        val nanoId = nanoId()

        val findFileContentByNanoIdImpl = FindFileContentByNanoIdImpl(dir.absolutePath)

        //create new file
        File(dir, nanoId.toString()).createNewFile()

        val result = findFileContentByNanoIdImpl.findByNanoId(nanoId)

        result shouldNotBe null
    }


    @Test
    fun `find FileContent fails`(@TempDir dir: File) = runTest {
        val nanoId = nanoId()

        val findFileContentByNanoIdImpl = FindFileContentByNanoIdImpl(dir.absolutePath)

        val result = findFileContentByNanoIdImpl.findByNanoId(nanoId)

        result shouldBe null
    }
}