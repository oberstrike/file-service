package de.ma.impl.content.gateway

import de.ma.domain.content.*
import de.ma.domain.nanoid.NanoId
import de.ma.impl.content.repository.DataFileContentRepositoryImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepositoryImpl: DataFileContentRepositoryImpl
) : DataFileContentGateway {

    override suspend fun getContent(search: DataFileContentSearch): Result<DataFileContentShow> {
        val dataFileContentShow = dataFileContentRepositoryImpl.findByNanoId(search.id)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(dataFileContentShow)
    }

    override suspend fun saveContent(
        contentCreate: DataFileContentCreate,
        nanoId: NanoId
    ): Result<DataFileContentOverview> {
        val dataFileContentOverview = dataFileContentRepositoryImpl.save(contentCreate, nanoId)
            ?: return Result.failure(RuntimeException(""))

        return Result.success(dataFileContentOverview)
    }

    override suspend fun deleteContent(search: DataFileContentSearch): Result<Unit> {
        return try {
            if (dataFileContentRepositoryImpl.exists(search.id)?.delete() == true) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException("Could not delete"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}
