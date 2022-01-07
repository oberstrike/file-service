package de.ma.persistence.nanoid

import de.ma.domain.nanoid.NanoId

data class NanoIdDTO(
    override var value: String
) : NanoId

fun NanoId.toDTO(): NanoIdDTO = NanoIdDTO(value)
