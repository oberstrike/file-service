package de.ma.datafile

import de.ma.shared.PagedList
import de.ma.shared.PagedParams
import de.ma.shared.SearchParams

class ShowDataFileUseCaseImpl(
    private val dataFileGateway: DataFileGateway
) : ShowDataFileUseCase{

    override fun invoke(
        pagedParams: PagedParams,
        searchParams: SearchParams?
    ): Result<PagedList<DataFileShowView>> {
        return dataFileGateway.findAll(pagedParams, searchParams)

    }

}
