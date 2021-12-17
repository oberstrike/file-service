package de.ma.datafile.impl.management

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.datafile.impl.utils.NanoIdGatewayImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class DataFileManagementImplTest : AbstractImplTest() {

    private val createDataFileContentUseCase = mockk<CreateDataFileContentUseCase>()

    private val createDataFileUseCase = mockk<CreateDataFileUseCase>()

    private val deleteDataFileUseCase: DeleteDataFileUseCase = mockk()

    private val deleteDataFileContentUseCase = mockk<DeleteDataFileContentUseCase>()

    private val nanoIdGateway = NanoIdGatewayImpl()

    private val dataFileManagement = DataFileManagementImpl(
        createDataFileContentUseCase = createDataFileContentUseCase,
        createDataFileUseCase = createDataFileUseCase,
        deleteDataFileUseCase = deleteDataFileUseCase,
        deleteDataFileContentUseCase = deleteDataFileContentUseCase,
        nanoIdGateway = nanoIdGateway

    )

    @Test
    fun `create successfully a data file`() {
        withDataFileCreate("txt", "test") { dataFileCreate ->
            withDataFileOverview { dataFileOverview ->
                withDataFileContentOverview(12) { dataFileContentOverview ->
                    runBlocking {

                        coEvery { createDataFileUseCase(dataFileCreate) } returns Result.success(dataFileOverview)

                        coEvery {
                            createDataFileContentUseCase(
                                dataFileCreate.content,
                                dataFileOverview.id
                            )
                        } returns Result.success(dataFileContentOverview)

                        val result = dataFileManagement.createDataFile(dataFileCreate)

                        result.isSuccess shouldBe true


                        coVerify(exactly = 1) { createDataFileUseCase(dataFileCreate) }

                        coVerify(exactly = 1) {
                            createDataFileContentUseCase(
                                dataFileCreate.content,
                                dataFileOverview.id
                            )
                        }

                    }
                }


            }


        }


    }

    @Test
    fun deleteDataFile() {
    }
}