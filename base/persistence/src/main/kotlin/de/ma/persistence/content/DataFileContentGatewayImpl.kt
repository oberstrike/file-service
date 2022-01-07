package de.ma.persistence.content

import de.ma.domain.content.*
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DeleteParamsDataFile
import de.ma.persistence.content.repository.DataFileContentRepositoryImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepositoryImpl: de.ma.persistence.content.repository.DataFileContentRepositoryImpl
) : DataFileContentGateway {

    override suspend fun saveContent(
        contentCreate: DataFileContentCreate,
        dataFileOverview: DataFileOverview
    ): Result<DataFileContentOverview> {
        val dataFileContentOverview = dataFileContentRepositoryImpl.save(contentCreate, dataFileOverview)
            ?: return Result.failure(RuntimeException("Couldn't save content"))

        return Result.success(dataFileContentOverview)
    }


    override suspend fun getContent(dataFileSearchParams: DataFileSearchParams): Result<DataFileContentShow> {
        val dataFileContentShow = dataFileContentRepositoryImpl.find(dataFileSearchParams)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(dataFileContentShow)
    }

    override suspend fun deleteContent(deleteParams: DeleteParamsDataFile): Result<Unit> {
        return try {
            if (dataFileContentRepositoryImpl.deleteByNanoId(deleteParams) == true) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException("File not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
