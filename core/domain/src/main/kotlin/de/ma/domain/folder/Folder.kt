package de.ma.domain.folder

import de.ma.domain.datafile.DataFile
import de.ma.domain.nanoid.NanoId

interface Folder {
    val name: String
    val dataFiles: List<DataFile>
    val id: NanoId?
}