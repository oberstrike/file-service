package de.ma.web.converter

import de.ma.domain.nanoid.NanoId
import java.lang.reflect.Type
import javax.ws.rs.ext.ParamConverter
import javax.ws.rs.ext.ParamConverterProvider
import javax.ws.rs.ext.Provider

@Provider
class CustomParamConverterProvider : ParamConverterProvider {


    private val converterByClass: Map<Class<*>, ParamConverter<*>> = mapOf(
        NanoId::class.java to NanoIdParamConverter()
    )


    override fun <T : Any?> getConverter(
        rawType: Class<T>,
        genericType: Type,
        annotations: Array<out Annotation>
    ): ParamConverter<T>? {
        if (converterByClass.containsKey(rawType)) {
            return converterByClass[rawType] as ParamConverter<T>
        }
        return null
    }


}

