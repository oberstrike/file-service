package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileSearch

interface DeleteDataFileUseCase {
    suspend operator fun<T : DataFileSearch> invoke(dataFileDelete: T): Result<Boolean>
}
