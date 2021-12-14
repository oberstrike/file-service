package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.content.DataFileContentGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test

class GetDataFileContentShowUseCaseImplTest {

    var dataFileGateway: DataFileGateway = mockk()

    var dataFileContentGateway: DataFileContentGateway = mockk()

    private val getDataFileContentUseCaseImpl = GetDataFileContentUseCaseImpl(
        dataFileGateway,
        dataFileContentGateway
    )


    @Test
    fun `Get content if data file doesn't exist throws an exception`() = runBlocking {
        val nanoId = nanoId("meineId")

        coEvery { dataFileGateway.findById(nanoId) } returns null

        val result = getDataFileContentUseCaseImpl.invoke(nanoId)

        result.isFailure shouldBe true

        result.exceptionOrNull() shouldNotBe null

        coVerify(exactly = 1) { dataFileGateway.findById(nanoId) }
    }

    @Test
    fun `Get content if data file is present successfully`() = runBlocking {
        val nanoId = nanoId("randomId123")

        val dataFileShow = dataFileShow(dataFileContentShow(file()), "test", "txt")

        coEvery { dataFileGateway.findById(nanoId) } returns dataFileShow

        coEvery { dataFileContentGateway.getContentByNanoId(nanoId) } returns Result.success(dataFileShow.content)

        val result = getDataFileContentUseCaseImpl(nanoId)

        result.isSuccess shouldBe true

        coVerify(exactly = 1) { dataFileGateway.findById(nanoId) }

        coVerify(exactly = 1) { dataFileContentGateway.getContentByNanoId(nanoId) }

    }
}