package de.ma.web.models

import de.ma.domain.datafile.DataFileUpdate
import de.ma.domain.nanoid.NanoId

data class DataFileUpdateForm(
    val id: String = "",
    val name: String = "",
    val extension: String = "",
    val version: Long = 0
){
    fun toDataFileUpdate(): DataFileUpdate {
        return DataFileUpdateImpl(
            NanoIdImpl(id),
            name,
            extension,
            version
        )
    }
}

data class DataFileUpdateImpl(
    override val id: NanoId = NanoIdImpl(""),
    override val name: String = "",
    override val extension: String = "",
    override val version: Long = 0
): DataFileUpdate
