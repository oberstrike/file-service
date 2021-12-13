package de.ma.domain.datafile

import de.ma.domain.shared.HasId

interface DataFileDelete : HasId<String> {
    val version: Long
}
