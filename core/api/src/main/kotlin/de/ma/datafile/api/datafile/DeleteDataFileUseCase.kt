package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileDelete

interface DeleteDataFileUseCase {
    suspend operator fun <T : DataFileDelete> invoke(dataFile: T): Result<Boolean>
}
