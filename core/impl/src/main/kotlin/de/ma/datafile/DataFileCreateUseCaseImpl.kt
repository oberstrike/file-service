package de.ma.datafile

import de.ma.datafile.api.management.DataFileCreateUseCase
import de.ma.datafile.internal.AlreadyExistsDataCreateFileFilter
import de.ma.datafile.internal.internalCreate
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId

class DataFileCreateUseCaseImpl(
    internal val dataFileGateway: DataFileGateway,
    internal val dataFileContentGateway: DataFileContentGateway,
    filters: List<DataCreateFileFilter> = listOf()
)  : DataFileCreateUseCase {

    private val _filters = listOf<DataCreateFileFilter>(AlreadyExistsDataCreateFileFilter(dataFileGateway)) + filters

    /*
        create Data File whole process
     */
    override suspend fun createDataFile(
        folderNanoId: NanoId,
        createDataFile: DataFileCreate
    ): Result<NanoId> {

        var target = createDataFile

        for (filter in _filters) {
            val newTarget = filter.accept(createDataFile)
            if (newTarget.isFailure) {
                return Result.failure(newTarget.exceptionOrNull() ?: RuntimeException("File already exists"))
            }
            target = newTarget.getOrNull()!!
        }

        return internalCreate(target, folderNanoId)
    }

}