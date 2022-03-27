package de.ma.persistence.datafile.content.repository.api

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DeleteDataFileParams
import de.ma.domain.nanoid.NanoId

interface DataFileContentRepository {

    suspend fun find(searchParams: DataFileSearchParams): DataFileContentShow?

    suspend fun save(content: DataFileContentCreate, nanoId: NanoId): DataFileContentOverview?

    suspend fun deleteAll()

    suspend fun delete(searchParams: DeleteDataFileParams): Boolean?

}
