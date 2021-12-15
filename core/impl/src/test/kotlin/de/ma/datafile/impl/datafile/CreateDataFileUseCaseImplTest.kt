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

    private val createDataFileContentUseCase = mockk<CreateDataFileContentUseCase>()

    private val createDataFileUseCase = CreateDataFileUseCaseImpl(
        createDataFileContentUseCase,
        gateway,
        NanoIdGatewayImpl()
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
            withDataFileShow { dataFileShow ->
                withDataFileOverview { dataFileOverview ->
                    withDataFileContentOverview(0) { dataFileContentOverview ->
                        runBlocking {
                            coEvery { gateway.save(dataFileCreate) } returns Result.success(dataFileOverview)

                            coEvery {
                                createDataFileContentUseCase.invoke(
                                    dataFileCreate.content
                                )
                            } returns Result.success(
                                dataFileContentOverview
                            )

                            val result = createDataFileUseCase(dataFileCreate)

                            //result is successfull and its the same like dataFileShow
                            result.isSuccess shouldBe true
                            result.getOrNull() shouldBe dataFileShow

                            coVerify(exactly = 1) { gateway.save(dataFileCreate) }

                            coVerify(exactly = 1) {
                                createDataFileContentUseCase.invoke(
                                    dataFileCreate.content
                                )
                            }

                        }
                    }
                }
            }
        }

    }

}
