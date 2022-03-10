package de.ma.persistence.shared.nanoid

import de.ma.domain.nanoid.NanoId
import java.io.Serializable

data class NanoIdEntity(override var id: String = "") : NanoId, Serializable
