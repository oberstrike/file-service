package de.ma.datafile

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.internal.*
import de.ma.datafile.internal.internalCreate
import de.ma.datafile.internal.internalDelete
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

class DataFileManagementUseCaseImpl(
    internal val dataFileGateway: DataFileGateway,
    internal val dataFileContentGateway: DataFileContentGateway
) : DataFileManagementUseCase {


    /*
        delete data file and data file content
     */
    override suspend fun deleteDataFile(dataFileDelete: DeleteDataFileParams): Result<Unit> {
        return internalDelete(dataFileDelete)
    }

    /*
        get data file and the data file content
     */
    override suspend fun getDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow> {
        return internalGetDataFile(dataFileSearchParams)
    }


    /*
        get data files paged
     */
    override suspend fun getDataFilesPaged(
        pagedParams: PagedParams,
        searchParams: DataFileSearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<DataFileShow>> {
        return internalGetDataFilesPaged(pagedParams, searchParams, sortParams)
    }


    override suspend fun updateDataFile(dataFileUpdate: DataFileUpdate): Result<DataFileShow> {
        return internalUpdate(dataFileUpdate)
    }

}