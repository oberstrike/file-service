package de.ma.datafile.api.content

import de.ma.domain.datafile.content.DataFileContentShow
import de.ma.domain.datafile.shared.NanoId

interface GetDataFileContentUseCase {
    suspend operator fun invoke(id: NanoId): Result<DataFileContentShow>
}