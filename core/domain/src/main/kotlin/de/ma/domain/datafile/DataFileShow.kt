package de.ma.domain.datafile

import de.ma.domain.content.DataFileContentShow

interface DataFileShow {
    val name: String
    val extension: String
    val content: DataFileContentShow
}