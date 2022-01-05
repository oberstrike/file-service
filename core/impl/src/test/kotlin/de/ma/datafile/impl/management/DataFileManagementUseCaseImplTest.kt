package de.ma.datafile.impl.management

import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.datafile.impl.utils.inputStream
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileGateway
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class DataFileManagementUseCaseImplTest : AbstractImplTest() {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val dataFileContentGateway = mockk<DataFileContentGateway>()

    private val dataFileManagement = DataFileManagementUseCaseImpl(
        dataFileGateway,
        dataFileContentGateway
    )

    @Test
    fun `create successfully a data file`() {
        val domain = "test"

        withDataFileContentCreate(inputStream()){
            dataFileContentCreate ->
            withDataFileCreate("txt", "testName", domain, dataFileContentCreate) { dataFileCreate ->
                withDataFileOverview(domain = domain) { dataFileOverview ->
                    withDataFileContentOverview(12) { dataFileContentOverview ->
                        runBlocking {

                            coEvery { dataFileGateway.save(dataFileCreate) } returns Result.success(dataFileOverview)

                            coEvery {
                                dataFileContentGateway.saveContent(
                                    dataFileCreate.content,
                                    dataFileOverview
                                )
                            } returns Result.success(dataFileContentOverview)

                            val result = dataFileManagement.dataFileCreate(dataFileCreate)

                            result.isSuccess shouldBe true


                            coVerify(exactly = 1) { dataFileGateway.save(dataFileCreate) }

                            coVerify(exactly = 1) {
                                dataFileContentGateway.saveContent(
                                    dataFileCreate.content,
                                    dataFileOverview
                                )
                            }
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