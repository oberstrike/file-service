package de.ma.datafile.api.content

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview

interface CreateDataFileContentUseCase {
    suspend operator fun invoke(content: DataFileContentCreate): Result<DataFileContentOverview>
}
