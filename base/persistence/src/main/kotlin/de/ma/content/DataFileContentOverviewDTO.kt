package de.ma.content

import de.ma.domain.content.DataFileContentOverview

data class DataFileContentOverviewDTO(
    override val size: Long
) : DataFileContentOverview