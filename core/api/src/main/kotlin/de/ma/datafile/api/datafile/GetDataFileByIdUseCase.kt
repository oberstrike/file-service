package de.ma.datafile.api.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.nanoid.NanoId

interface GetDataFileByIdUseCase {
    suspend operator fun invoke(id: NanoId): Result<DataFileOverview>
}