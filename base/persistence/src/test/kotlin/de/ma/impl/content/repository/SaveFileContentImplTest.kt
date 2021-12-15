package de.ma.impl.content.repository

import de.ma.impl.utils.AbstractDatabaseTest
import io.quarkus.test.junit.QuarkusTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@QuarkusTest
class SaveFileContentImplTest : AbstractDatabaseTest() {


    private val saveFileContentImpl = SaveFileContentImpl("domain")

    @Test
    fun save() = runTest {
        withContentCreate { contentCreate ->
            saveFileContentImpl.save(contentCreate., "content")
        }

    }
}
