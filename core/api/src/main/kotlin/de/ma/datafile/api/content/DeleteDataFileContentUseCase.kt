package de.ma.datafile.api.content

import de.ma.domain.content.DataFileContentDelete

interface DeleteDataFileContentUseCase {
    suspend operator fun invoke(dataFileDelete: DataFileContentDelete): Result<Unit>
}