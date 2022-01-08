package de.ma.domain.datafile.exceptions

//DataFile specific exceptions
sealed class DataFileException(
    message: String
) : RuntimeException(message) {

    data class NotFoundException(val id: String) : DataFileException(
        "DataFile with id '$id' not found"
    )

    data class AlreadyExistsException(val name: String, val extension: String, val domain: String) : DataFileException(
        "DataFile with name '$name' and extension '$extension' and domain '$domain' already exists"
    )

    class InvalidDataFileException(message: String) : DataFileException(message)

}
