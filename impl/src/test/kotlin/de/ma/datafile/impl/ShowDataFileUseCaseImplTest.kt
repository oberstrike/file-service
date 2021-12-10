package de.ma.datafile.impl

import de.ma.datafile.impl.ShowDataFilesUseCaseImpl
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShowView
import de.ma.datafile.impl.utils.pagedList
import de.ma.datafile.impl.utils.pagedParams
import de.ma.datafile.impl.utils.searchParams
import de.ma.domain.datafile.shared.PagedList
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class ShowDataFileUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val showDataFileUseCase = ShowDataFilesUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `test if everything is returned`() {

        val pagedList : PagedList<DataFileShowView> = pagedList()

        every { dataFileGateway.findAll(any(), any()) } returns Result.success(pagedList)

        val result = showDataFileUseCase.invoke(
            pagedParams(),
            searchParams()
        )

        result.isSuccess shouldBe true
        result.getOrNull() shouldBe pagedList

    }
}
