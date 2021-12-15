package de.ma.datafile.api.content

import de.ma.domain.content.DataFileContentSearch
import de.ma.domain.content.DataFileContentShow

interface GetDataFileContentUseCase {
    suspend operator fun invoke(search: DataFileContentSearch): Result<DataFileContentShow>
}
