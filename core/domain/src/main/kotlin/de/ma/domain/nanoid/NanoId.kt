package de.ma.domain.nanoid

import java.io.Serializable

interface NanoId : Serializable {
    var id: String
}