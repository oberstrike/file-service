package de.ma.content

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepository: DataFileContentRepository
) : DataFileContentGateway {

    override suspend fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow> {
        val dataFileContentShow = dataFileContentRepository.findByNanoId(nanoId)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(dataFileContentShow)
    }

    override suspend fun saveContentByNanoId(
        nanoId: NanoId,
        content: DataFileContentCreate
    ): Result<DataFileContentOverview> {
        val dataFileContentOverview = dataFileContentRepository.save(nanoId, content)
            ?: return Result.failure(RuntimeException(""))

        return Result.success(dataFileContentOverview)
    }

    override suspend fun deleteContentByNanoId(nanoId: NanoId): Boolean {
        return try {
            dataFileContentRepository.exists(nanoId)?.delete() ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


}