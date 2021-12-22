package de.ma.impl.content

import de.ma.domain.content.DataFileContentDelete
import de.ma.domain.nanoid.NanoId

data class DataFileContentDeleteDTO(
    override val id: NanoId
): DataFileContentDelete