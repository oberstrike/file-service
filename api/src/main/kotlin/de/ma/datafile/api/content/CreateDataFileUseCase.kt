package de.ma.datafile.api.content

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShowView

interface CreateDataFileUseCase {
    operator fun invoke(dataFileCreate: DataFileCreate): Result<DataFileShowView>
}
