package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.nanoid.NanoId

data class DataFileOverviewDTO(
    override val id: NanoId,
    override val name: String,
) : DataFileOverview


fun DataFileEntity.toOverviewDTO() : DataFileOverview = DataFileOverviewDTO(
    id = id!!,
    name = name,
)