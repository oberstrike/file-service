package de.ma.datafile

import de.ma.datafile.DataFileGateway
import de.ma.datafile.SaveDataFileUseCaseImpl
import de.ma.datafile.utils.dataFileCreate
import de.ma.datafile.utils.dataFileShow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SaveDataFileUseCaseImplTest {

    private val gateway = mockk<DataFileGateway>()

    private val saveDataFileUseCase = SaveDataFileUseCaseImpl(
        gateway
    )

    @Test
    fun `the save fails`(){
        val testFile = SaveDataFileUseCaseImpl::class.java.getResource("/files/TestDatei-Klein.txt")?:
        throw IllegalStateException("Test file not found")
        //reads the file with stream and prints all lines of its content
        val testFileContent = testFile.openStream()
        val dataFileCreate = dataFileCreate(testFileContent, "TestDatei-Klein.txt")


        every { gateway.save(dataFileCreate) } returns Result.failure(Exception())

        val result = saveDataFileUseCase(dataFileCreate)

        result.isFailure shouldBe true
        result.exceptionOrNull()?.message shouldBe "There was an error saving the data file"

        verify(exactly = 1) { gateway.save(dataFileCreate) }

    }


    @Test
    fun `saves successfully a datafile`() {
        val testFile = SaveDataFileUseCaseImpl::class.java.getResource("/files/TestDatei-Klein.txt")?:
            throw IllegalStateException("Test file not found")
        //reads the file with stream and prints all lines of its content
        val testFileContent = testFile.openStream()

        val dataFileCreate = dataFileCreate(testFileContent, "TestDatei-Klein.txt")
        val dataFileShow = dataFileShow("123")

        every { gateway.save(dataFileCreate) } returns Result.success(dataFileShow)

        val result = saveDataFileUseCase(dataFileCreate)

        //result is successfull and its the same like dataFileShow
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe dataFileShow

        verify(exactly = 1) { gateway.save(dataFileCreate) }

    }

}
