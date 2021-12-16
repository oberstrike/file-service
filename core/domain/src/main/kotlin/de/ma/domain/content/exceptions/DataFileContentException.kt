package de.ma.domain.content.exceptions

sealed class DataFileContentException(
    message: String
) : RuntimeException(message) {

    data class NotFoundException(val id: String) : DataFileContentException(
        "data file content with id '$id' not found"
    )

    class InvalidException(message: String) : DataFileContentException(message)

}