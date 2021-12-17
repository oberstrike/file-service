package de.ma.datafile.impl.datafile

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class CreateDataFileUseCaseImplTest : AbstractImplTest() {

    private val gateway = mockk<DataFileGateway>()


    private val createDataFileUseCase = CreateDataFileUseCaseImpl(
        gateway
    )

    @Test
    fun `the save fails`() {
        withDataFileCreate("txt", "test") { dataFileCreate ->
            runBlocking {

                coEvery { gateway.save(dataFileCreate) } returns Result.failure(Exception())

                val result = createDataFileUseCase(dataFileCreate)

                result.isFailure shouldBe true
                result.exceptionOrNull()?.message shouldBe "There was an error saving the data file"

                coVerify(exactly = 1) { gateway.save(dataFileCreate) }
            }
        }
    }


    @Test
    fun `saves successfully a datafile`() {
        withDataFileCreate("txt", "test") { dataFileCreate ->
            withDataFileOverview { dataFileOverview ->
                runBlocking {
                    coEvery { gateway.save(dataFileCreate) } returns Result.success(dataFileOverview)

                    val result = createDataFileUseCase(dataFileCreate)

                    //result is successfull and its the same like dataFileShow
                    result.isSuccess shouldBe true
                    result.getOrNull() shouldBe dataFileOverview

                    coVerify(exactly = 1) { gateway.save(dataFileCreate) }


                }
            }
        }
    }
}
