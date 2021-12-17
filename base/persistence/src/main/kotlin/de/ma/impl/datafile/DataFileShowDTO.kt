package de.ma.impl.datafile

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileShow

data class DataFileShowDTO(
    override val extension: String,
    override val name: String,
) : DataFileShow{

    override lateinit var content: DataFileContentShow

}