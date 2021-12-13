package de.ma.domain.datafile

import de.ma.domain.shared.HasId
import de.ma.domain.nanoid.NanoId

interface DataFile<T : NanoId> {
    var id: T?
    val name: String
    val extension: String
    val size: Long
}

