package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

interface GetDataFilesUseCase {
    suspend operator fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null,
        sortParams: SortParams? = null
    ): Result<PagedList<DataFileOverview>>
}
