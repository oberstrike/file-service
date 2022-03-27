package de.ma.web.models

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId

class DataFileSearchParamsImpl(
    override val id: NanoId?
) : DataFileSearchParams