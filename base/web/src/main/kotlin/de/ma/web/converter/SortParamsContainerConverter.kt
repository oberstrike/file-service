package de.ma.web.converter

import de.ma.domain.shared.SortParam
import de.ma.web.models.SortParamsContainer
import de.ma.web.models.SortParamsImpl
import javax.ws.rs.ext.ParamConverter

class SortParamsContainerConverter: ParamConverter<SortParamsContainer> {

    override fun toString(p0: SortParamsContainer): String {
        val sb = StringBuilder()

        for (i in p0.sortParams.indices) {
            val sortParam = p0.sortParams[i]
            val isLast = i == p0.sortParams.size - 1

            sb += "${sortParam.sortBy}:${sortParam.direction}"

            if (!isLast) {
                sb += ","
            }
        }

        return sb.toString()
    }

    override fun fromString(p0: String): SortParamsContainer {
        val sortParams = mutableListOf<SortParam>()

        val regex = Regex("(\\w+):(asc|desc)")
        val matches = regex.findAll(p0)

        for (match in matches) {
            val sortBy = match.groupValues[1]
            val direction = match.groupValues[2]
            sortParams.add(SortParamsImpl(sortBy, direction))
        }

        return SortParamsContainer(sortParams)
    }
}

private operator fun java.lang.StringBuilder.plusAssign(s: String) {
    this.append(s)
}
