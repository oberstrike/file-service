package de.ma.persistence.datafile.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate

data class DataFileCreateDTO(
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String
) : DataFileCreate
