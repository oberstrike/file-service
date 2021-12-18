package de.ma.web.datafile

import de.ma.domain.nanoid.NanoId

data class NanoIdImpl(
    override var value: String
) : NanoId

fun String.toNanoId(): NanoId {
    return NanoIdImpl(this)
}