package de.ma.datafile.impl.management.internal

import de.ma.datafile.impl.management.DataFileManagementUseCaseImpl
import de.ma.domain.datafile.DataFile
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import java.lang.RuntimeException

suspend fun DataFileManagementUseCaseImpl.internalGetDataFilesPaged(
    pagedParams: PagedParams,
    searchParams: SearchParams?,
    sortParams: SortParams?
): Result<PagedList<DataFileOverview>>  {
    val result = dataFileGateway.findAll(pagedParams, searchParams, sortParams)

    if (result.isFailure) {
        println("Could not get datafiles")
        println(result.exceptionOrNull()?.message ?: "No error message")
        return Result.failure(RuntimeException("Could not get datafiles"))
    }
    return result
}