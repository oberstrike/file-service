package de.ma.web.datafile

import de.ma.domain.datafile.DataFileSearch
import javax.ws.rs.ext.ParamConverter

class DataFileConverter : ParamConverter<DataFileSearch> {

    override fun fromString(value: String): DataFileSearch {
        return DataFileSearchImpl(NanoIdImpl(value))
    }

    override fun toString(value: DataFileSearch): String {
        return value.id.value
    }

}