package de.ma.web.infrastructure

import de.ma.domain.datafile.DataFileSearch
import de.ma.web.datafile.DataFileConverter
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
        if (rawType == DataFileSearch::class.java) {
            return DataFileConverter() as ParamConverter<T>
        }
        return null
    }


}

