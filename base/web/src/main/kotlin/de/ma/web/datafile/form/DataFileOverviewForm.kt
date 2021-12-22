package de.ma.web.datafile.form

import de.ma.domain.datafile.DataFileOverview

data class DataFileOverviewForm(
    val id: String,
    val name: String
)

fun DataFileOverview.toImpl(): DataFileOverviewForm {
    return DataFileOverviewForm(
        id = id.value,
        name = name
    )
}