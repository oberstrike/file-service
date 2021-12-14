package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileOverview

data class DataFileOverviewDTO(
    override val id: String,
    override val name: String,
    override val size: Long
) : DataFileOverview


fun DataFileEntity.toOverviewDTO() : DataFileOverview = DataFileOverviewDTO(
    id = id!!.text,
    name = name,
    size = size,
)