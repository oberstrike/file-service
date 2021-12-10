package de.ma.domain.datafile.content

import de.ma.domain.datafile.shared.NanoId

interface DataFileContentGateway {

    fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow>

    fun saveContentByNanoId(nanoId: NanoId, content: DataFileContentCreate): Result<DataFileContentOverview>

    fun deleteContentByNanoId(nanoId: NanoId): Boolean
}