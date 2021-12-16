package de.ma.datafile.api.content

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.nanoid.NanoId

interface CreateDataFileContentUseCase {
    suspend operator fun invoke(content: DataFileContentCreate, nanoId: NanoId): Result<DataFileContentOverview>
}
