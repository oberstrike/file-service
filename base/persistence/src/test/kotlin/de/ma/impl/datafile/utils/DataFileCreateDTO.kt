package de.ma.impl.datafile.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate

data class DataFileCreateDTO(
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String,
    override val domain: String
) : DataFileCreate
