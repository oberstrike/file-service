package de.ma.domain.datafile

import de.ma.domain.folder.Folder
import de.ma.domain.nanoid.NanoId
import java.time.LocalDateTime

//The whole DataFile domain splitted up to smaller parts like Delete, Create, Overview and Show
interface DataFile  {
    val name: String
    val extension: String
    val createdAt: LocalDateTime
    val folder: Folder?
    val id: NanoId?
}

