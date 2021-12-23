package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.nanoid.NanoId
import java.time.LocalDateTime

data class DataFileOverviewDTO(
    override val id: NanoId,
    override val name: String,
    override val createdAt: LocalDateTime,
    override val extension: String
) : DataFileOverview


fun DataFileEntity.toOverviewDTO() : DataFileOverview = DataFileOverviewDTO(
    id = id!!,
    name = name,
    createdAt = createdAt,
    extension = extension
)