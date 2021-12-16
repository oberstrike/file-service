package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId

interface DataFileUpdate: HasId<NanoId> {
    val size: Long
}