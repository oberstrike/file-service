package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileOverview

interface CreateDataFileUseCase {
    suspend operator fun<T: DataFileCreate> invoke(dataFileCreate: T): Result<DataFileOverview>
}
