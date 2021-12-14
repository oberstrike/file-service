package de.ma.impl.content

import de.ma.domain.content.DataFileContentShow
import java.io.File

data class DataFileContentShowDTO(
    override val file: File
) : DataFileContentShow