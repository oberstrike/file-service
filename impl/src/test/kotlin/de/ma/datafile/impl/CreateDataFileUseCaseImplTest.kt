package de.ma.datafile.impl

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.impl.utils.*
import de.ma.domain.datafile.DataFileGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class CreateDataFileUseCaseImplTest {

    private val gateway = mockk<DataFileGateway>()


    private val createDataFileContentUseCase = mockk<CreateDataFileContentUseCase>()


    private val createDataFileUseCase = CreateDataFileUseCaseImpl(
        createDataFileContentUseCase,
        gateway,
        NanoIdGatewayImpl()
    )

    @Test
    fun `the save fails`() {
        val testFile = CreateDataFileUseCaseImpl::class.java.getResource("/files/TestDatei-Klein.txt")
            ?: throw IllegalStateException("Test file not found")
        //reads the file with stream and prints all lines of its content
        val dataFileContent = dataFileContentCreate(testFile.openStream())
        val dataFileCreate = dataFileCreate(dataFileContent, "TestDatei-Klein.txt")


        every { gateway.save(dataFileCreate) } returns Result.failure(Exception())

        val result = createDataFileUseCase(dataFileCreate)

        result.isFailure shouldBe true
        result.exceptionOrNull()?.message shouldBe "There was an error saving the data file"

        verify(exactly = 1) { gateway.save(dataFileCreate) }

    }


    @Test
    fun `saves successfully a datafile`() {
        val contentCreate = dataFileContentCreate(inputStream())
        val dataFileCreate = dataFileCreate(contentCreate, "TestDatei-Klein.txt")
        val dataFileShow = dataFileShow("123")

        val dataFileContentOverview = dataFileContentOverview(123)


        every { gateway.save(dataFileCreate) } returns Result.success(dataFileShow)

        every { createDataFileContentUseCase.invoke(contentCreate, nanoId("123")) } returns Result.success(
            dataFileContentOverview
        )

        val result = createDataFileUseCase(dataFileCreate)

        //result is successfull and its the same like dataFileShow
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe dataFileShow

        verify(exactly = 1) { gateway.save(dataFileCreate) }

        verify(exactly = 1) { createDataFileContentUseCase.invoke(contentCreate, nanoId("123")) }

    }

}
