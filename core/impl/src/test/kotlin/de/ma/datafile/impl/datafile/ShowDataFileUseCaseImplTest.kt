package de.ma.datafile.impl.datafile

import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileOverview
import de.ma.datafile.impl.utils.pagedList
import de.ma.datafile.impl.utils.pagedParams
import de.ma.datafile.impl.utils.searchParams
import de.ma.domain.shared.PagedList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

class ShowDataFileUseCaseImplTest {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val showDataFileUseCase = GetDataFilesUseCaseImpl(
        dataFileGateway
    )

    @Test
    fun `test if everything is returned`() = runBlocking{

        val pagedList : PagedList<DataFileOverview> = pagedList()

        coEvery { dataFileGateway.findAll(any(), any(), any()) } returns Result.success(pagedList)

        val result = showDataFileUseCase.invoke(
            pagedParams(),
            searchParams()
        )

        result.isSuccess shouldBe true
        result.getOrNull() shouldBe pagedList
        Unit
    }
}

