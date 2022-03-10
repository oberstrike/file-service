package de.ma.domain.folder

import de.ma.domain.nanoid.NanoId

interface FolderSearchParams {
    val id: NanoId?
    val name: String?
}