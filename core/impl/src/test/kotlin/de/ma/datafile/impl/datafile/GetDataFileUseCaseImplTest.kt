package de.ma.datafile.impl.datafile

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class GetDataFileUseCaseImplTest : AbstractImplTest(){

    private val dataFileGateway = mockk<DataFileGateway>()

    val getDataFileByIdUseCaseImpl = GetDataFileUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `get a valid data file by id`() = runTest {
        val nanoId = nanoId()

        withDataFileSearch(nanoId) { dataFileSearch ->
            withDataFileShow { dataFileShow ->
                runBlocking {
                    coEvery { dataFileGateway.find(dataFileSearch) } returns dataFileShow

                    val result = getDataFileByIdUseCaseImpl(dataFileSearch)

                    result.isSuccess shouldBe true
                    result.getOrNull() shouldBe dataFileShow

                    coVerify(exactly = 1) { dataFileGateway.find(dataFileSearch) }
                }

            }
        }



    }
}
