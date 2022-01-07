package de.ma.persistence.datafile

import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileShow

data class DataFileShowDTO(
    override val extension: String,
    override val name: String,
    override val domain: String?
) : DataFileShow{

    override lateinit var content: DataFileContentShow

}
