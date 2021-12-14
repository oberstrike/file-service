package de.ma.impl.nanoid

import de.ma.domain.nanoid.NanoId
import java.io.Serializable

data class NanoIdEntity(override var text: String = "") : NanoId, Serializable

