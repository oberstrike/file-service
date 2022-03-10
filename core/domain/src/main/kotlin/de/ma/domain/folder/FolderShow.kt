package de.ma.domain.folder

import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId

interface FolderShow {
    val name: String
    val size: Long
    val id: NanoId
    val dataFiles: List<DataFileShow>
}