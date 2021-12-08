package de.ma.datafile

import de.ma.datafile.DataFileGateway
import de.ma.datafile.DeleteDataFileUseCaseImpl
import de.ma.datafile.exceptions.DataFileException
import de.ma.datafile.utils.dataFileDelete
import de.ma.datafile.utils.dataFileShow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class DeleteDataFileUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val deletedDataFileUseCaseImpl = DeleteDataFileUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `try to delete a data file but throws an exception because its not present`() {
        val dataFileDelete = dataFileDelete("123")

        every { dataFileGateway.findById(dataFileDelete.id) } returns null

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isFailure `should be` true

        result.exceptionOrNull()?.javaClass `should be` DataFileException.NotFoundException::class.java

        verify(exactly = 1) { dataFileGateway.findById(dataFileDelete.id) }

        verify(exactly = 0) { dataFileGateway.deleteById(any()) }
    }

    @Test
    fun `tests if it throws an exception when the versions are different`() {
        val dataFileDelete = dataFileDelete("123", 1)
        val dataFileShowView = dataFileShow("123", 2)

        every { dataFileGateway.findById(dataFileDelete.id) } returns dataFileShowView

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isFailure `should be` true

        result.exceptionOrNull()?.javaClass `should be` DataFileException.VersionException::class.java

        verify(exactly = 1) { dataFileGateway.findById(dataFileDelete.id) }

        verify(exactly = 0) { dataFileGateway.deleteById(any()) }
    }

    @Test
    fun `test to delete a data file successfully`() {
        val dataFileDelete = dataFileDelete("123", 1)
        val dataFileShowView = dataFileShow("123", 1)

        every { dataFileGateway.findById(dataFileDelete.id) } returns dataFileShowView

        every { dataFileGateway.deleteById(dataFileDelete.id) } returns true

        val result = deletedDataFileUseCaseImpl(dataFileDelete)

        result.isSuccess `should be` true
        result.getOrNull() `should be` true

        verify(exactly = 1) { dataFileGateway.findById(dataFileDelete.id) }

        verify(exactly = 1) { dataFileGateway.deleteById(any()) }
    }


}
