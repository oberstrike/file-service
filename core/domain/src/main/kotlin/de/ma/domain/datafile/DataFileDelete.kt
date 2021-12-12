package de.ma.domain.datafile

import de.ma.domain.datafile.shared.HasId

interface DataFileDelete : HasId<String> {
    val version: Long
}
