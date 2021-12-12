package de.ma.domain.datafile.com

import de.ma.domain.datafile.content.DataFileContentCreate

interface DataFileCreate {
    val name: String
    val content: DataFileContentCreate
}
