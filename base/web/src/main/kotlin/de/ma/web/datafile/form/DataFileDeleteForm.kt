package de.ma.web.datafile.form

import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.nanoid.NanoId

data class DataFileDeleteForm(
    override val id: NanoId
) : DataFileDelete