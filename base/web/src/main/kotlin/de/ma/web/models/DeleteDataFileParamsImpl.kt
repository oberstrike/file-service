package de.ma.web.models

import de.ma.domain.datafile.DeleteDataFileParams
import de.ma.domain.nanoid.NanoId

data class DeleteDataFileParamsImpl(
    override val id: NanoId
): DeleteDataFileParams
