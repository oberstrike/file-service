package de.ma.datafile.api.management

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.datafile.DataFileShow

interface DataFileManagement {
    suspend fun createDataFile(createDataFile: DataFileCreate): Result<Unit>

    suspend fun deleteDataFile(deleteDataFile: DataFileDelete): Result<Unit>

    suspend fun getDataFile(dataFileSearch: DataFileSearch): Result<DataFileShow>
}
