package de.ma.datafile.api.content

interface DeleteDataFileContentUseCase {
    suspend operator fun invoke(dataFileContentId: String): Result<Unit>
}