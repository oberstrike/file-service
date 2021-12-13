package de.ma.datafile.api.content

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId

interface GetDataFileContentUseCase {
    suspend operator fun invoke(id: NanoId): Result<DataFileContentShow>
}