package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.content.DataFileContentGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test

class GetDataFileContentShowUseCaseImplTest : AbstractImplTest() {

    var dataFileGateway: DataFileGateway = mockk()

    var dataFileContentGateway: DataFileContentGateway = mockk()

    private val getDataFileContentUseCaseImpl = GetDataFileContentUseCaseImpl(
        dataFileGateway,
        dataFileContentGateway
    )


    @Test
    fun `Get content if data file doesn't exist throws an exception`() = runBlocking {
        val nanoId = nanoId("meineId")


        coEvery { dataFileGateway.find(nanoId) } returns null

        val result = getDataFileContentUseCaseImpl(nanoId)

        result.isFailure shouldBe true

        result.exceptionOrNull() shouldNotBe null

        coVerify(exactly = 1) { dataFileGateway.find(nanoId) }
    }

    @Test
    fun `Get content if data file is present successfully`()  {
        withDataFileShow { dataFileShow ->
            val nanoId = nanoId()

            val dataFileContentShow = dataFileShow.content

            coEvery { dataFileGateway.find(nanoId) } returns dataFileShow

            coEvery { dataFileContentGateway.getContent(nanoId) } returns Result.success(dataFileShow.content)

            val result = getDataFileContentUseCaseImpl(de.ma.datafile.impl.utils.nanoId)

            result.isSuccess shouldBe true

            coVerify(exactly = 1) { dataFileGateway.find(de.ma.datafile.impl.utils.nanoId) }

            coVerify(exactly = 1) { dataFileContentGateway.getContent(de.ma.datafile.impl.utils.nanoId) }
        }



    }
}
