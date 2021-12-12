package de.ma.datafile.api.content

import de.ma.domain.datafile.com.DataFileCreate
import de.ma.domain.datafile.DataFileShowView

interface CreateDataFileUseCase {
    suspend operator fun<T: DataFileCreate> invoke(dataFileCreate: T): Result<DataFileShowView>
}
