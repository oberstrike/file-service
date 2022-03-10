package de.ma.persistence.datafile.content.data

import de.ma.domain.content.DataFileContentShow
import java.io.File

data class DataFileContentShowDTO(
    override val file: File,
    override val size: Long
) : DataFileContentShow
