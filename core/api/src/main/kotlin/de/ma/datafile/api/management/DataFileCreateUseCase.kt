package de.ma.datafile.api.management

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId

interface DataFileCreateUseCase {
    suspend fun createDataFile(
        folderNanoId: NanoId,
        createDataFile: DataFileCreate
    ): Result<NanoId>
}