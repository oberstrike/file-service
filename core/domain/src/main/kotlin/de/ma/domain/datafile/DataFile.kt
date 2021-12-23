package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import java.time.LocalDateTime

//The whole DataFile domain splitted up to smaller parts like Delete, Create, Overview and Show
interface DataFile : HasId<NanoId?>  {
    val name: String
    val extension: String
    val createdAt: LocalDateTime
}

