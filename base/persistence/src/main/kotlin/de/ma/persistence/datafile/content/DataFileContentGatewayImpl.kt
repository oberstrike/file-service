package de.ma.persistence.datafile.content

import de.ma.domain.content.*
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.datafile.DeleteDataFileParams
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.content.repository.DataFileContentRepositoryImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepositoryImpl: DataFileContentRepositoryImpl
) : DataFileContentGateway {

    override suspend fun getContent(dataFileSearchParams: DataFileSearchParams): Result<DataFileContentShow> {
        val dataFileContentShow = dataFileContentRepositoryImpl.find(dataFileSearchParams)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(dataFileContentShow)
    }

    override suspend fun saveContent(
        contentCreate: DataFileContentCreate,
        nanoId: NanoId
    ): Result<DataFileContentOverview> {
        val dataFileContentOverviewResult = dataFileContentRepositoryImpl.save(contentCreate, nanoId)

        return if(dataFileContentOverviewResult == null) {
            Result.failure(RuntimeException("File not found"))
        } else {
            Result.success(dataFileContentOverviewResult)
        }
    }

    override suspend fun deleteContent(deleteParams: DeleteDataFileParams): Result<Unit> {
        return try {
            if (dataFileContentRepositoryImpl.delete(deleteParams) == true) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException("File not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
