package de.ma.datafile.api.content

import de.ma.domain.datafile.DataFileDelete

interface DeleteDataFileUseCase {
    operator fun <T : DataFileDelete> invoke(dataFile: T): Result<Boolean>
}
