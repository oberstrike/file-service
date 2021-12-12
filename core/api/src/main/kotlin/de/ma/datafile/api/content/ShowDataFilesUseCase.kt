package de.ma.datafile.api.content

import de.ma.domain.datafile.DataFileShowView
import de.ma.domain.datafile.shared.PagedList
import de.ma.domain.datafile.shared.PagedParams
import de.ma.domain.datafile.shared.SearchParams

interface ShowDataFilesUseCase {
    operator fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams? = null
    ): Result<PagedList<DataFileShowView>>
}
