package de.ma.domain.datafile.exceptions

sealed class DataFileException(
    message: String
) : RuntimeException(message) {
    data class NotFoundException(val id: String) : DataFileException(
        "DataFile with id '$id' not found"
    )

    data class VersionException(val id: String, val version: Long) : DataFileException(
        "DataFile with id '$id' has an old version: '$version'"
    )

    class InvalidDataFileException(message: String) : DataFileException(message)

}
