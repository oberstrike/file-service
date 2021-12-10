package de.ma.datafile.impl.content

import de.ma.datafile.impl.utils.dataFileContentShow
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.content.DataFileContentGateway
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.datafile.impl.utils.dataFileShow
import de.ma.datafile.impl.utils.file
import de.ma.datafile.impl.utils.nanoId
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test

class GetDataFileContentShowUseCaseImplTest {

    var dataFileGateway: DataFileGateway = mockk()

    var dataFileContentGateway: DataFileContentGateway = mockk()

    private val getDataFileContentUseCaseImpl = GetDataFileContentUseCaseImpl(
        dataFileGateway,
        dataFileContentGateway
    )


    @Test
    fun `Get content if data file doesn't exist throws an exception`() {
        val nanoId = nanoId("meineId")

        every { dataFileGateway.findById(nanoId) } returns null

        val result = getDataFileContentUseCaseImpl.invoke(nanoId)

        result.isFailure shouldBe true

        result.exceptionOrNull() shouldNotBe null

        verify(exactly = 1) { dataFileGateway.findById(nanoId) }
    }

    @Test
    fun `Get content if data file is present successfully`() {
        val nanoId = nanoId("randomId123")
        val dataFileShow = dataFileShow(nanoId.text)
        val dataFileContentShow = dataFileContentShow(file())

        every { dataFileGateway.findById(nanoId) } returns dataFileShow

        every { dataFileContentGateway.getContentByNanoId(nanoId) } returns Result.success(dataFileContentShow)

        val result = getDataFileContentUseCaseImpl(nanoId)

        result.isSuccess shouldBe true

        verify(exactly = 1) { dataFileGateway.findById(nanoId) }

        verify(exactly = 1) { dataFileContentGateway.getContentByNanoId(nanoId) }

    }
}