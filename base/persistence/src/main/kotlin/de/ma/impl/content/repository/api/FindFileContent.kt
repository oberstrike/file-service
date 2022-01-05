package de.ma.impl.content.repository.api

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams

interface FindFileContent {
    suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow?

}
