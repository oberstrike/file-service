package de.ma.datafile

import de.ma.shared.PagedList
import de.ma.shared.PagedParams
import de.ma.shared.SearchParams

interface ShowDataFileUseCase {
    operator fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null
    ): Result<PagedList<DataFileShowView>>
}
