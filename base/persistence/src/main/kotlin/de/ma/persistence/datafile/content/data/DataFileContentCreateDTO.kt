package de.ma.persistence.datafile.content.data

import de.ma.domain.content.DataFileContentCreate
import java.io.InputStream

data class DataFileContentCreateDTO(
    override val input: InputStream
): DataFileContentCreate
