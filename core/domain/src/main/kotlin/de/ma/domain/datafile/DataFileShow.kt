package de.ma.domain.datafile

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId

//DataFileShow shows the data file content together with name and extension
interface DataFileShow {
    val name: String
    val extension: String
    var content: DataFileContentShow
    val id: NanoId

    fun toSearchParams(): DataFileSearchParams
}
