package de.ma.datafile

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileShow

data class DataFileShowDTO(
    override val content: DataFileContentShow,
    override val extension: String,
    override val name: String
) : DataFileShow