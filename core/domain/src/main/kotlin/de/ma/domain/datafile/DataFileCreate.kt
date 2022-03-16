package de.ma.domain.datafile

import de.ma.domain.content.DataFileContentCreate

//The interface that is used when a new DataFile should be created
interface DataFileCreate {
    val name: String
    val extension: String
    val content: DataFileContentCreate
}
