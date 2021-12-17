package de.ma.domain.datafile

import de.ma.domain.content.DataFileContentShow

//DataFileShow shows the data file content together with name and extension
interface DataFileShow {
    val name: String
    val extension: String
    var content: DataFileContentShow
}
