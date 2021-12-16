package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.datafile.DataFileShow

interface GetDataFileUseCase {
    suspend operator fun invoke(dataFileSearch: DataFileSearch): Result<DataFileShow>
}