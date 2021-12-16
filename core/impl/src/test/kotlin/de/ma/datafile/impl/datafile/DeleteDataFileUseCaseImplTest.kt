package de.ma.datafile.impl.datafile

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class DeleteDataFileUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val nanoIdGateway = NanoIdGatewayImpl()

    private val deletedDataFileUseCaseImpl = DeleteDataFileUseCaseImpl(
        dataFileGateway,
        nanoIdGateway
    )

    private fun String.toNanoId() = nanoIdGateway.toNanoId(this)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `try to delete a data file but throws an exception because its not present`() = runTest {
        val dataFileDelete = dataFileDelete("123")

        val nanoId = dataFileDelete.id.toNanoId()

        coEvery { dataFileGateway.find(nanoId) } returns null

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isFailure `should be` true

        result.exceptionOrNull()?.javaClass `should be` DataFileException.NotFoundException::class.java

        coVerify (exactly = 1) { dataFileGateway.find(nanoId) }

        coVerify(exactly = 0) { dataFileGateway.delete(any()) }
    }


    @Test
    fun `test to delete a data file successfully`() = runTest {
        val dataFileDelete = dataFileDelete("123", 1)
        val dataFileShowView = dataFileShow(dataFileContentShow(file()), "123", "txt")

        val nanoId = dataFileDelete.id.toNanoId()

        coEvery { dataFileGateway.find(nanoId) } returns dataFileShowView

        coEvery { dataFileGateway.delete(nanoId) } returns true

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isSuccess `should be` true
        result.getOrNull() `should be` true

        coVerify(exactly = 1) { dataFileGateway.find(nanoId) }

        coVerify(exactly = 1) { dataFileGateway.delete(any()) }
    }


}
