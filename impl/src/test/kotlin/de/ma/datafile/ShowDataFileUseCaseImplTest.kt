package de.ma.datafile

import de.ma.datafile.utils.pagedList
import de.ma.datafile.utils.pagedParams
import de.ma.datafile.utils.searchParams
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class ShowDataFileUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val showDataFileUseCase = ShowDataFileUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `test if everything is returned`() {

        val pagedList = pagedList()

        every { dataFileGateway.findAll(any(), any()) } returns Result.success(pagedList)

        val result = showDataFileUseCase.invoke(
            pagedParams(),
            searchParams()
        )

        result.isSuccess shouldBe true
        result.getOrNull() shouldBe pagedList

    }
}
