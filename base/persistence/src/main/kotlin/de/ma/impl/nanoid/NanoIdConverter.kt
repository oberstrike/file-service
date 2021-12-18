package de.ma.impl.nanoid

import javax.enterprise.context.ApplicationScoped
import javax.persistence.AttributeConverter

@ApplicationScoped
class NanoIdConverter : AttributeConverter<NanoIdEntity, String> {

    override fun convertToEntityAttribute(dbData: String): NanoIdEntity {
        return NanoIdEntity(dbData)
    }

    override fun convertToDatabaseColumn(attribute: NanoIdEntity?): String {
        return attribute?.value ?: ""
    }

}