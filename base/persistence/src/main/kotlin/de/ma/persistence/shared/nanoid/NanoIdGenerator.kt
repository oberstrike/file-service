package de.ma.persistence.shared.nanoid

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

@SuppressWarnings("unused")
class NanoIdGenerator : IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor?, any: Any?): Serializable {
        return NanoIdEntity(NanoIdUtils.randomNanoId())
    }

}

