package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId

interface DataFileUpdate {
    val id: NanoId
    val name: String?
    val extension: String?
    val version: Long
}