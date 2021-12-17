package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.content.DataFileContentGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class GetDataFileContentShowUseCaseImplTest : AbstractImplTest() {


    var dataFileContentGateway: DataFileContentGateway = mockk()

    private val getDataFileContentUseCaseImpl = GetDataFileContentUseCaseImpl(
        dataFileContentGateway
    )


    @Test
    fun `Get content if data file doesn't exist throws an exception`() {

        val nanoId = nanoId("meineId")
        withDataFileContentSearch(nanoId) { dataFileContentSearch ->
            runBlocking {

                coEvery { dataFileContentGateway.getContent(dataFileContentSearch) } returns Result.failure(
                    RuntimeException("Test")
                )


                val result = getDataFileContentUseCaseImpl(dataFileContentSearch)

                result.isFailure shouldBe true

                result.exceptionOrNull() shouldNotBe null

                coVerify(exactly = 1) { dataFileContentGateway.getContent(dataFileContentSearch) }
            }
        }

    }

    @Test
    fun `Get content if data file is present successfully`() {
        val nanoId = nanoId("meineId")
        withDataFileContentSearch(nanoId) { dataFileContentSearch ->
            withDataFileContentShow(file()) { dataFileContentShow ->
                runBlocking {

                    coEvery { dataFileContentGateway.getContent(dataFileContentSearch) } returns Result.success(
                        dataFileContentShow
                    )

                    val result = getDataFileContentUseCaseImpl(dataFileContentSearch)

                    result.isSuccess shouldBe true

                    result.getOrNull() shouldBe dataFileContentShow

                    coVerify(exactly = 1) { dataFileContentGateway.getContent(dataFileContentSearch) }
                }
            }


        }

    }
}
