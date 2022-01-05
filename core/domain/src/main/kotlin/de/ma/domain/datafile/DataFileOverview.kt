package de.ma.domain.datafile

import java.time.LocalDateTime

//DataFileOverview is for the overview of the datafiles
interface DataFileOverview : DataFileSearchParams {
    val name: String
    val createdAt: LocalDateTime
    val extension: String
}
