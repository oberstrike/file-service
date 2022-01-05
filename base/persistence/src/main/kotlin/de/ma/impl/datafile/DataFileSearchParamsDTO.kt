package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

data class DataFileSearchParamsDTO(
    override val id: NanoId,
    override val domain: String?
    ): DataFileSearchParams