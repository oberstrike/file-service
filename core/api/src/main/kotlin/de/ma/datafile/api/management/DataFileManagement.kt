package de.ma.datafile.api.management

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete

interface DataFileManagement {
    suspend fun createDataFile(createDataFile: DataFileCreate): Result<Unit>

    suspend fun deleteDataFile(deleteDataFile: DataFileDelete): Result<Unit>
}