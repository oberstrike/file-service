package de.ma.persistence.datafile.data

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.data.DataFileSearchParamsDTO

data class DataFileShowDTO(
    override val extension: String,
    override val name: String,
    override val id: NanoId
) : DataFileShow{

    override lateinit var content: DataFileContentShow

    override fun toSearchParams(): DataFileSearchParams {
        return DataFileSearchParamsDTO(id)
    }

}
