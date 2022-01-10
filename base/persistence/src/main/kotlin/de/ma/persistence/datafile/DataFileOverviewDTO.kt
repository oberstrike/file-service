package de.ma.persistence.datafile

import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.nanoid.NanoId
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.LocalDateTime

@RegisterForReflection
data class DataFileOverviewDTO(
    override val id: NanoId,
    override val name: String,
    override val createdAt: LocalDateTime,
    override val extension: String,
    override val domain: String?
) : DataFileOverview


fun DataFileEntity.toOverviewDTO() : DataFileOverview = DataFileOverviewDTO(
    id = id!!,
    name = name,
    createdAt = createdAt,
    extension = extension,
    domain = domain
)
