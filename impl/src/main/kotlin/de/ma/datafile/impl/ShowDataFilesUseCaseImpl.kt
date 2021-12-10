package de.ma.datafile.impl

import de.ma.datafile.api.content.ShowDataFilesUseCase
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShowView
import de.ma.domain.datafile.shared.PagedList
import de.ma.domain.datafile.shared.PagedParams
import de.ma.domain.datafile.shared.SearchParams

class ShowDataFilesUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : ShowDataFilesUseCase {

    override fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileShowView>> {
        return dataFileGateway.findAll(pagedParams, searchParams)

    }

}
