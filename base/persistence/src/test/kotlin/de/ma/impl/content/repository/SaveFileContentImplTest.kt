package de.ma.impl.content.repository

import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should not be empty`
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.rules.TemporaryFolder
import java.io.File
import java.nio.file.Path

@QuarkusTest
class SaveFileContentImplTest : AbstractDatabaseTest() {

    @Test
    fun save(@TempDir dir: File) {
        val saveFileContentImpl = SaveFileContentImpl(dir.absolutePath)

        val nanoId = nanoId()

        withContentCreate { contentCreate ->
            runBlocking {
                saveFileContentImpl.save(contentCreate, nanoId)

                val listFiles = dir.listFiles()
                listFiles shouldNotBe null
                listFiles?.`should not be empty`()
            }

        }

    }
}
