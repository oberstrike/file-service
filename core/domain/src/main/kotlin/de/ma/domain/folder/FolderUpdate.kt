package de.ma.domain.folder

import de.ma.domain.nanoid.NanoId

interface FolderUpdate {
    val name: String
    val id: NanoId
}