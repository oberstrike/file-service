package de.ma.datafile.api.content

import de.ma.domain.datafile.DataFileDelete

interface DeleteDataFileUseCase {
   operator fun invoke(dataFile: DataFileDelete): Result<Boolean>
}
