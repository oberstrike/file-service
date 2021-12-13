package de.ma.domain.datafile

import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.shared.HasId

interface DataFileOverview : HasId<String> {
    val name: String
    val dataFileContentOverview: DataFileContentOverview
}
