package de.ma.domain.datafile

import de.ma.domain.datafile.shared.HasId
import de.ma.domain.datafile.shared.NanoId

interface DataFile : HasId<NanoId> {
    val name: String
    val extension: String
    val size: Long
    val lastSeen: Long
    val version: Long
}

