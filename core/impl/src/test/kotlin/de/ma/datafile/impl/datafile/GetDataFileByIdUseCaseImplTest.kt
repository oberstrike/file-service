package de.ma.datafile.impl.datafile

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GetDataFileByIdUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    val getDataFileByIdUseCaseImpl = GetDataFileByIdUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `get a valid data file by id`() = runTest {
        val nanoId = nanoId("123")
        val dataFileContentShow = dataFileContentShow(file())
        val dataFileShow = dataFileShow(dataFileContentShow, "123", "txt")

        coEvery { dataFileGateway.find(nanoId) } returns dataFileShow


        val result = getDataFileByIdUseCaseImpl(nanoId)

        coVerify(exactly = 1) { dataFileGateway.find(nanoId) }

    }
}
