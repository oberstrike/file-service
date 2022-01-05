package de.ma.web.infrastructure.converter

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.web.datafile.DataFileSearchParamsImpl
import de.ma.web.datafile.NanoIdImpl
import javax.ws.rs.ext.ParamConverter

class DataFileSearchConverter : ParamConverter<DataFileSearchParams> {

    override fun fromString(value: String): DataFileSearchParams {
        return DataFileSearchParamsImpl(NanoIdImpl(value))
    }

    override fun toString(value: DataFileSearchParams): String {
        return value.id.value
    }

}