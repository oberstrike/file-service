package de.ma.impl.content.gateway

import de.ma.domain.content.*
import de.ma.impl.content.repository.DataFileContentRepositoryImpl
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepositoryImpl: DataFileContentRepositoryImpl
) : DataFileContentGateway {

    override suspend fun getContent(search: DataFileContentSearch): Result<DataFileContentShow> {
        val dataFileContentShow = dataFileContentRepositoryImpl.findByNanoId(search)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(dataFileContentShow)
    }

    override suspend fun saveContent(
        contentCreate: DataFileContentCreate
    ): Result<DataFileContentOverview> {
        val dataFileContentOverview = dataFileContentRepositoryImpl.save(contentCreate.id, contentCreate)
            ?: return Result.failure(RuntimeException(""))

        return Result.success(dataFileContentOverview)
    }

    override suspend fun deleteContent(search: DataFileContentSearch): Boolean {
        return try {
            dataFileContentRepositoryImpl.exists(search)?.delete() ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


}
