package de.ma.datafile.impl.datafile

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class DeleteDataFileUseCaseImplTest : AbstractImplTest() {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val nanoIdGateway = NanoIdGatewayImpl()

    private val deletedDataFileUseCaseImpl = DeleteDataFileUseCaseImpl(
        dataFileGateway,
        nanoIdGateway
    )


    @Test
    fun `try to delete a data file but throws an exception because its not present`() {

        withDataFileDelete { dataFileDelete ->
            runBlocking {

                coEvery { dataFileGateway.delete(dataFileDelete) } returns Result.failure(
                    DataFileException.NotFoundException(
                        "DataFile not found"
                    )
                )

                val result = deletedDataFileUseCaseImpl(dataFileDelete)

                coVerify { dataFileGateway.delete(dataFileDelete) }

                result.isFailure `should be` true

                result.exceptionOrNull()?.javaClass `should be` DataFileException.NotFoundException::class.java


                coVerify(exactly = 1) { dataFileGateway.delete(any()) }
            }

        }
    }


    @Test
    fun `test to delete a data file successfully`() {
        withDataFileDelete { dataFileDelete ->
            runBlocking {

                coEvery { dataFileGateway.delete(dataFileDelete) } returns Result.success(true)

                val result = deletedDataFileUseCaseImpl(dataFileDelete)

                result.isSuccess `should be` true

                result.getOrNull() `should be` true

                coVerify(exactly = 1) { dataFileGateway.delete(any()) }

            }
        }


    }


}
