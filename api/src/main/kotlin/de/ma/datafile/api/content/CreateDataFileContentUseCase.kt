package de.ma.datafile.api.content

import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentOverview
import de.ma.domain.datafile.shared.NanoId

interface CreateDataFileContentUseCase {
    suspend operator fun invoke(content: DataFileContentCreate, id: NanoId): Result<DataFileContentOverview>
}