package de.ma.datafile.impl.management

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.datafile.impl.utils.NanoIdGatewayImpl
import de.ma.domain.content.DataFileContentGateway
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

    private val getDataFileUseCase = mockk<GetDataFileUseCase>()

    private val getDataFileContentUseCase = mockk<GetDataFileContentUseCase>()

    private val dataFileContentGateway = mockk<DataFileContentGateway>()

    private val dataFileManagement = DataFileManagementImpl(
        createDataFileContentUseCase = createDataFileContentUseCase,
        createDataFileUseCase = createDataFileUseCase,
        deleteDataFileUseCase = deleteDataFileUseCase,
        deleteDataFileContentUseCase = deleteDataFileContentUseCase,
        getDataFileUseCase,
        getDataFileContentUseCase,
        dataFileContentGateway
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