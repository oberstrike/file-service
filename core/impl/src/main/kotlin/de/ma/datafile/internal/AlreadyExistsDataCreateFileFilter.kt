package de.ma.datafile.internal

import de.ma.datafile.DataCreateFileFilter
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway

class AlreadyExistsDataCreateFileFilter(
    private val dataFileGateway: DataFileGateway
) : DataCreateFileFilter {
    override suspend fun accept(createDataFile: DataFileCreate): Result<DataFileCreate> {
        val exists = dataFileGateway.exists(createDataFile.name, createDataFile.extension, createDataFile.domain)
        return if (exists) Result.failure(RuntimeException("Data file already exists"))
        else Result.success(createDataFile)
    }
}
