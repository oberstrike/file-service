package de.ma.persistence.nanoid

import de.ma.domain.nanoid.NanoId
import java.io.Serializable

data class NanoIdEntity(override var value: String = "") : NanoId, Serializable
