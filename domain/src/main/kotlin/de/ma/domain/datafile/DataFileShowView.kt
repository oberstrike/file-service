package de.ma.domain.datafile

import de.ma.domain.datafile.shared.HasId
import de.ma.domain.datafile.shared.NanoId

interface DataFileShowView : HasId<String> {
    val name: String
    val size: Long
    val version: Long
}
