package de.ma.domain.datafile.exceptions

sealed class DataFileException(
    message: String
) : RuntimeException(message) {
    data class NotFoundException(val id: String) : DataFileException(
        "DataFile with id '$id' not found"
    )

    class InvalidDataFileException(message: String) : DataFileException(message)

}
