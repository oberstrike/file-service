package de.ma.nanoid

import de.ma.domain.nanoid.NanoId
import java.io.Serializable

data class NanoIdEntity(override val text: String = "") : NanoId, Serializable