package de.ma.datafile.internal

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam
import java.lang.RuntimeException

suspend fun DataFileManagementUseCaseImpl.internalGetDataFilesPaged(
    pagedParams: PagedParams,
    searchParams: DataFileSearchParams?,
    sortParams: SortParam?
): Result<PagedList<DataFileShow>>  {
    val result = dataFileGateway.findAll(pagedParams, searchParams, sortParams)

    if (result.isFailure) {
        println("Could not get datafiles")
        println(result.exceptionOrNull()?.message ?: "No error message")
        return Result.failure(RuntimeException("Could not get datafiles"))
    }
    return result
}