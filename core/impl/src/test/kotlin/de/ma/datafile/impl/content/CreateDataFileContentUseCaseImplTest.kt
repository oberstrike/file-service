package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.datafile.impl.utils.inputStream
import de.ma.datafile.impl.utils.nanoId
import de.ma.domain.content.DataFileContentGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class CreateDataFileContentUseCaseImplTest : AbstractImplTest() {

    private val dataFileContentGateway = mockk<DataFileContentGateway>()

    private val createDataFileContentUseCaseImpl = CreateDataFileContentUseCaseImpl(
        dataFileContentGateway
    )

    @Test
    fun `checks if returns the right value if all is successfully done`() {
        withDataFileContentCreate(inputStream()) { dataFileContentCreate ->
            withDataFileContentOverview(12) { overview ->
                runBlocking {
                    val nanoId = nanoId()

                    coEvery { dataFileContentGateway.saveContent(dataFileContentCreate, nanoId) } returns
                            Result.success(overview)

                    val result = createDataFileContentUseCaseImpl.invoke(dataFileContentCreate, nanoId)

                    result.isSuccess shouldBe true

                    coVerify(exactly = 1) { dataFileContentGateway.saveContent(dataFileContentCreate, nanoId) }

                }
            }


        }


    }
}
