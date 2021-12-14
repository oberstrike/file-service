package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId

interface DataFile<T : NanoId> : HasId<T?> {
    val name: String
    val extension: String
    val size: Long
}

