package de.ma.datafile

import java.io.InputStream

interface DataFileCreate {
    val name: String
    val inputStream: InputStream
}
