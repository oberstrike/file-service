package de.ma.domain.datafile.shared

interface NanoIdGateway {
    fun toNanoId(text: String): NanoId
    fun toText(nanoId: NanoId): String
}