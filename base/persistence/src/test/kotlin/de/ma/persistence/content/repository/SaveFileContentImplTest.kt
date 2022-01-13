package de.ma.persistence.content.repository

import de.ma.persistence.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should not be empty`
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

@QuarkusTest
class SaveFileContentImplTest : AbstractDatabaseTest() {

    @Test
    fun `save successfully a new file content`(@TempDir targetDir: File) {
        val saveFileContentImpl = SaveFileContentImpl(targetDir.absolutePath)

        val nanoId = nanoId()
        val searchParams = searchParams(nanoId, null)

        val inputText = "Mein Text"

        withContentCreate(inputText) { contentCreate ->
            runBlocking {
                saveFileContentImpl.save(contentCreate, searchParams)

                val listFiles = targetDir.listFiles()
                listFiles shouldNotBe null
                listFiles?.`should not be empty`()

                val file = listFiles[0]
                //check if file content equals to inputText
                val content = file.readText()
                content shouldNotBe null
                assert(content == inputText)

            }
        }
    }
}