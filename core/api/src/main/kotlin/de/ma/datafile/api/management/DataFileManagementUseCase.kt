package de.ma.datafile.api.management

import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam

interface DataFileManagementUseCase {

    suspend fun deleteDataFile(dataFileDelete: DeleteDataFileParams): Result<Unit>

    suspend fun getDataFile(dataFileSearchParams: DataFileSearchParams): Result<DataFileShow>

    suspend fun getDataFilesPaged(
        pagedParams: PagedParams,
        searchParams: DataFileSearchParams? = null,
        sortParams: SortParam? = null
    ): Result<PagedList<DataFileShow>>

    suspend fun createDataFile(
        folderNanoId: NanoId,
        createDataFile: DataFileCreate
    ): Result<DataFileShow>

    suspend fun updateDataFile(dataFileUpdate: DataFileUpdate): Result<DataFileShow>
}
