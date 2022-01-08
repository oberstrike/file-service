package de.ma.web.datafile

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

class DataFileSearchParamsImpl(
    override val id: NanoId,
    override val domain: String
) : DataFileSearchParams