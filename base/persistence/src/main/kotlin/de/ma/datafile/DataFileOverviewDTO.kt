package de.ma.datafile

import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.datafile.DataFileOverview

data class DataFileOverviewDTO(
    override val id: String,
    override val dataFileContentOverview: DataFileContentOverview,
    override val name: String
): DataFileOverview