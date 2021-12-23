package de.ma.datafile.impl.management

import de.ma.datafile.impl.utils.AbstractImplTest
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
        withDataFileCreate("txt", "test") { dataFileCreate ->
            withDataFileOverview { dataFileOverview ->
                withDataFileContentOverview(12) { dataFileContentOverview ->
                    runBlocking {

                        coEvery { dataFileGateway.save(dataFileCreate) } returns Result.success(dataFileOverview)

                        coEvery {
                            dataFileContentGateway.saveContent(
                                dataFileCreate.content,
                                dataFileOverview.id
                            )
                        } returns Result.success(dataFileContentOverview)

                        val result = dataFileManagement.dataFileCreate(dataFileCreate)

                        result.isSuccess shouldBe true


                        coVerify(exactly = 1) { dataFileGateway.save(dataFileCreate) }

                        coVerify(exactly = 1) {
                            dataFileContentGateway.saveContent(
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