package de.ma.web.converter

import de.ma.domain.nanoid.NanoId
import de.ma.web.models.NanoIdImpl
import javax.ws.rs.ext.ParamConverter

class NanoIdParamConverter: ParamConverter<NanoId> {

    override fun fromString(value: String): NanoId {
        return NanoIdImpl(value)
    }

    override fun toString(value: NanoId): String {
        return value.id
    }

}