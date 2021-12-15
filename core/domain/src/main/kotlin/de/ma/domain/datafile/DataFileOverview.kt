package de.ma.domain.datafile

import de.ma.domain.shared.HasId

//DataFileOverview is for the overview of the datafiles
interface DataFileOverview : HasId<String> {
    val name: String
    val size: Long
}
