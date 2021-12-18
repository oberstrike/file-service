package de.ma.web.datafile

import de.ma.domain.datafile.DataFileOverview

data class DataFileOverviewImpl(
    val id: String,
    val name: String
)

fun DataFileOverview.toImpl(): DataFileOverviewImpl {
    return DataFileOverviewImpl(
        id = id.value,
        name = name
    )
}