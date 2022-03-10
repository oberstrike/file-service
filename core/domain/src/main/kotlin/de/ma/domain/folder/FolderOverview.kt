package de.ma.domain.folder

import de.ma.domain.nanoid.NanoId

interface FolderOverview {
    val name: String
    var size: Long
    val id: NanoId
}