package de.ma.domain.datafile

import de.ma.domain.datafile.shared.HasId
import de.ma.domain.datafile.shared.NanoId

interface DataFileDelete : HasId<String> {
    val version: Long
}
