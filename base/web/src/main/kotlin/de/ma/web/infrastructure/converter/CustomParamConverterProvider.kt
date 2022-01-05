package de.ma.web.infrastructure.converter

import de.ma.domain.datafile.DataFileSearchParams
import java.lang.reflect.Type
import javax.ws.rs.ext.ParamConverter
import javax.ws.rs.ext.ParamConverterProvider
import javax.ws.rs.ext.Provider

@Provider
class CustomParamConverterProvider : ParamConverterProvider {

    override fun <T : Any?> getConverter(
        rawType: Class<T>,
        genericType: Type,
        annotations: Array<out Annotation>
    ): ParamConverter<T>? {
        //returns a DataFileConverter if rawType is DataFileSearch
        if (rawType == DataFileSearchParams::class.java) {
            return DataFileSearchConverter() as ParamConverter<T>
        }
        return null
    }


}

