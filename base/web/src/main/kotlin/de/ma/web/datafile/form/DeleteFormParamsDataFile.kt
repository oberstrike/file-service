package de.ma.web.datafile.form

import de.ma.domain.datafile.DeleteParamsDataFile
import de.ma.domain.nanoid.NanoId

data class DeleteFormParamsDataFile(
    override val id: NanoId
) : DeleteParamsDataFile