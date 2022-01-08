package de.ma.datafile.impl.management

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.impl.management.internal.internalCreate
import de.ma.datafile.impl.management.internal.internalDelete
import de.ma.datafile.impl.management.internal.internalGetDataFile
import de.ma.datafile.impl.management.internal.internalGetDataFilesPaged
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.*
import de.ma.domain.datafile.exceptions.DataFileException
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams

class DataFileManagementUseCaseImpl(
    internal val dataFileGateway: DataFileGateway,
    internal val dataFileContentGateway: DataFileContentGateway
) : DataFileManagementUseCase {

    /*
        create Data File whole process
     */
    override suspend fun dataFileCreate(createDataFile: DataFileCreate): Result<DataFileOverview> {

        val alreadyExists: Boolean = dataFileGateway.exists(createDataFile.name, createDataFile.extension, createDataFile.domain)

        if(alreadyExists) {
            return Result.failure(DataFileException.AlreadyExistsException(createDataFile.name, createDataFile.extension, createDataFile.domain))
        }

        return internalCreate(createDataFile)
    }

    /*
        delete data file and data file content
     */
    override suspend fun deleteDataFile(dataFileDelete: DeleteParamsDataFile): Result<Unit> {
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
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>> {
        return internalGetDataFilesPaged(pagedParams, searchParams, sortParams)
    }

}