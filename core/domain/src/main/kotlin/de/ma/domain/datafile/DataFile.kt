package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId

//The whole DataFile domain splitted up to smaller parts like Delete, Create, Overview and Show
interface DataFile<T : NanoId> : HasId<T?> {
    val name: String
    val extension: String
}

