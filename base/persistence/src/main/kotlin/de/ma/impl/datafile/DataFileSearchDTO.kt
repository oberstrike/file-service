package de.ma.impl.datafile

import de.ma.domain.datafile.DataFileSearch
import de.ma.domain.nanoid.NanoId

data class DataFileSearchDTO(override val id: NanoId): DataFileSearch