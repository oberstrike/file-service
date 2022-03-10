package de.ma.persistence.datafile.data

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

data class DataFileSearchParamsDTO(
    override val id: NanoId
) : DataFileSearchParams
