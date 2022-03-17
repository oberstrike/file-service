package de.ma.datafile

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.internal.*
import de.ma.datafile.internal.internalCreate
import de.ma.datafile.internal.internalDelete
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.*
import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam

class DataFileManagementUseCaseImpl(
    internal val dataFileGateway: DataFileGateway,
    internal val dataFileContentGateway: DataFileContentGateway,
    private val filters: List<DataCreateFileFilter> = listOf()
) : DataFileManagementUseCase {

    private val _filters = listOf<DataCreateFileFilter>(AlreadyExistsDataCreateFileFilter(dataFileGateway)) + filters


    /*
        create Data File whole process
     */
    override suspend fun createDataFile(
        folderSearchParams: FolderSearchParams,
        createDataFile: DataFileCreate
    ): Result<DataFileShow> {

        var target = createDataFile
        for (filter in _filters) {
            val newTarget = filter.accept(createDataFile)
            if (newTarget.isFailure) {
                return Result.failure(newTarget.exceptionOrNull() ?: RuntimeException("File already exists"))
            }
            target = newTarget.getOrNull()!!
        }

        return internalCreate(target)
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
        sortParams: SortParam?
    ): Result<PagedList<DataFileShow>> {
        return internalGetDataFilesPaged(pagedParams, searchParams, sortParams)
    }

}