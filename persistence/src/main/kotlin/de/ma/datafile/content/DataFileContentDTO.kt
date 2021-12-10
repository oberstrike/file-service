package de.ma.datafile.content

import de.ma.domain.datafile.content.DataFileContentShow
import java.io.File

data class DataFileContentDTO(
    override val content: File
) : DataFileContentShow