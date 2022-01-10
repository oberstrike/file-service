package de.ma.datafile.impl.management

import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.datafile.impl.utils.inputStream
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.exceptions.DataFileException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
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
    fun `if a file already exists it throws an exception`() = runTest {
        val domain = "test"
        val extension = "txt"
        val name = "testName"

        withDataFileContentCreate(inputStream()) { dataFileContentCreate ->
            withDataFileCreate(extension, name, domain, dataFileContentCreate) { dataFileCreate ->
                runBlocking {

                    coEvery { dataFileGateway.exists(name, extension, domain) } returns true

                    val result = dataFileManagement.dataFileCreate(dataFileCreate)

                    result.isFailure shouldBe true

                    result.exceptionOrNull()!!::class shouldBe DataFileException.AlreadyExistsException::class

                }
            }
        }


    }


    @Test
    fun `create successfully a data file`() {
        val domain = "test"

        withDataFileContentCreate(inputStream()){
            dataFileContentCreate ->
            withDataFileCreate("txt", "testName", domain, dataFileContentCreate) { dataFileCreate ->
                withDataFileOverview(domain = domain) { dataFileOverview ->
                    withDataFileContentOverview(12) { dataFileContentOverview ->
                        runBlocking {

                            coEvery { dataFileGateway.exists("testName", "txt", domain) } returns false

                            coEvery { dataFileGateway.save(dataFileCreate) } returns Result.success(dataFileOverview)

                            coEvery {
                                dataFileContentGateway.saveContent(
                                    dataFileCreate.content,
                                    dataFileOverview
                                )
                            } returns Result.success(dataFileContentOverview)

                            val result = dataFileManagement.dataFileCreate(dataFileCreate)

                            result.isSuccess shouldBe true

                            coVerify(exactly = 1) {
                                dataFileGateway.exists("testName", "txt", domain)
                                dataFileGateway.save(dataFileCreate)
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