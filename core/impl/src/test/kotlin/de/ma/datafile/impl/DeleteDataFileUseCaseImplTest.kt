package de.ma.datafile.impl

import de.ma.datafile.impl.datafile.DeleteDataFileUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.datafile.impl.utils.NanoIdGatewayImpl
import de.ma.datafile.impl.utils.dataFileDelete
import de.ma.datafile.impl.utils.dataFileShow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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


    @Test
    fun `try to delete a data file but throws an exception because its not present`() {
        val dataFileDelete = dataFileDelete("123")

        val nanoId = dataFileDelete.id.toNanoId()

        every { dataFileGateway.findById(nanoId) } returns null

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isFailure `should be` true

        result.exceptionOrNull()?.javaClass `should be` DataFileException.NotFoundException::class.java

        verify(exactly = 1) { dataFileGateway.findById(nanoId) }

        verify(exactly = 0) { dataFileGateway.deleteById(any()) }
    }

    @Test
    fun `tests if it throws an exception when the versions are different`() {
        val dataFileDelete = dataFileDelete("123", 1)
        val dataFileShowView = dataFileShow("123", 2)

        every { dataFileGateway.findById(dataFileDelete.id.toNanoId()) } returns dataFileShowView

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isFailure `should be` true

        result.exceptionOrNull()?.javaClass `should be` DataFileException.VersionException::class.java

        verify(exactly = 1) { dataFileGateway.findById(dataFileDelete.id.toNanoId()) }

        verify(exactly = 0) { dataFileGateway.deleteById(any()) }
    }

    @Test
    fun `test to delete a data file successfully`() {
        val dataFileDelete = dataFileDelete("123", 1)
        val dataFileShowView = dataFileShow("123", 1)

        val nanoId = dataFileDelete.id.toNanoId()

        every { dataFileGateway.findById(nanoId) } returns dataFileShowView

        every { dataFileGateway.deleteById(nanoId) } returns true

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isSuccess `should be` true
        result.getOrNull() `should be` true

        verify(exactly = 1) { dataFileGateway.findById(nanoId) }

        verify(exactly = 1) { dataFileGateway.deleteById(any()) }
    }


}
