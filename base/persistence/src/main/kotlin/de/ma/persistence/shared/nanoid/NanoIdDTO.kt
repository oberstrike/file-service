package de.ma.persistence.shared.nanoid

import de.ma.domain.nanoid.NanoId

data class NanoIdDTO(
    override var id: String
) : NanoId

fun NanoId.toDTO(): NanoIdDTO = NanoIdDTO(id)
