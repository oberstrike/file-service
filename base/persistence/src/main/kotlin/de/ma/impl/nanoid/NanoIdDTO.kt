package de.ma.impl.nanoid

import de.ma.domain.nanoid.NanoId

data class NanoIdDTO(
    override val text: String
) : NanoId

fun NanoIdEntity.toDTO(): NanoIdDTO = NanoIdDTO(text)