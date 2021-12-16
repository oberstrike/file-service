package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.nanoid.NanoId

interface DeleteDataFileUseCase {
    suspend operator fun invoke(nanoId: NanoId): Result<Boolean>
}
