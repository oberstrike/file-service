package de.ma.datafile

interface SaveDataFileUseCase {
    operator fun invoke(dataFile: DataFileCreate): Result<DataFileShowView>
}
