package de.ma.impl.nanoid

import de.ma.domain.nanoid.NanoId
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class NanoIdConverter : AttributeConverter<NanoId, String> {

    override fun convertToEntityAttribute(dbData: String): NanoId {
        println("convertToEntityAttribute: $dbData")
        return NanoIdDTO(dbData)
    }

    override fun convertToDatabaseColumn(attribute: NanoId): String {
        println("convertToDatabaseColumn: ${attribute.value}")
        return attribute.value
    }

}