package de.ma.datafile

import de.ma.domain.datafile.DataFileCreate

interface DataCreateFileFilter {
    suspend fun accept(createDataFile: DataFileCreate): Result<DataFileCreate>
}