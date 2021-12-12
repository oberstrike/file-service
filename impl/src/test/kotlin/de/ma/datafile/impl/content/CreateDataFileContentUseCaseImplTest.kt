package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.dataFileContentCreate
import de.ma.datafile.impl.utils.dataFileContentOverview
import de.ma.datafile.impl.utils.inputStream
import de.ma.datafile.impl.utils.nanoId
import de.ma.domain.datafile.content.DataFileContentGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CreateDataFileContentUseCaseImplTest {

    private val dataFileContentGateway = mockk<DataFileContentGateway>()

    private val createDataFileContentUseCaseImpl = CreateDataFileContentUseCaseImpl(
        dataFileContentGateway
    )

    @Test
    fun `checks if returns the right value if all is successfully done`() = runBlocking {
        val dataFileContentCreate = dataFileContentCreate(inputStream())
        val nanoId = nanoId("123")

        coEvery { dataFileContentGateway.saveContentByNanoId(nanoId, dataFileContentCreate) } returns
                Result.success(dataFileContentOverview(123))

        val result = createDataFileContentUseCaseImpl(dataFileContentCreate, nanoId)

        result.isSuccess shouldBe true

        coVerify(exactly = 1) { dataFileContentGateway.saveContentByNanoId(nanoId, dataFileContentCreate) }


    }
}