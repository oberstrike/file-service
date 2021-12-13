package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams

interface ShowDataFilesUseCase {
    suspend operator fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null
    ): Result<PagedList<DataFileOverview>>
}
