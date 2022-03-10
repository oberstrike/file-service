package de.ma.persistence.datafile.content.data

import de.ma.domain.content.DataFileContentOverview

data class DataFileContentOverviewDTO(
    override val size: Long
) : DataFileContentOverview
