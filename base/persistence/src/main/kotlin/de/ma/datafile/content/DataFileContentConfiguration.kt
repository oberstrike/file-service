package de.ma.datafile.content

import org.eclipse.microprofile.config.inject.ConfigProperties

@ConfigProperties(prefix = "datafile.content")
data class DataFileContentConfiguration(
    val folder: String = ""
)
