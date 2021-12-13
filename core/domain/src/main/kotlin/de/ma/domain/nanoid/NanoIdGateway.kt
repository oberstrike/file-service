package de.ma.domain.nanoid

interface NanoIdGateway {
    fun toNanoId(text: String): NanoId
    fun toText(nanoId: NanoId): String
}