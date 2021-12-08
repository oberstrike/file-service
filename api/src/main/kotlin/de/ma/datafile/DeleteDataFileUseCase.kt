package de.ma.datafile

interface DeleteDataFileUseCase {
    operator fun invoke(dataFile: DataFileDelete): Result<Boolean>
}
