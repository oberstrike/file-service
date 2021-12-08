package de.ma.datafile

interface DataFile {
    val id: String
    val name: String
    val extension: String
    val size: Long
    val lastModified: Long
    val version: Long
}

