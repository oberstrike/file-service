package de.ma.domain.datafile

import java.time.LocalDateTime

//DataFileOverview is for the overview of the datafiles
interface DataFileOverview : DataFileSearch {
    val name: String
    val createdAt: LocalDateTime
    val extension: String
}
