package de.ma.datafile.content

import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentGateway
import de.ma.domain.datafile.content.DataFileContentOverview
import de.ma.domain.datafile.content.DataFileContentShow
import de.ma.domain.datafile.shared.NanoId
import java.io.File
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepository: DataFileContentRepository
) : DataFileContentGateway {

    override suspend fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow> {
        val byteArray: File = dataFileContentRepository.findByNanoId(nanoId)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(DataFileContentDTO(byteArray))
    }

    override suspend fun saveContentByNanoId(
        nanoId: NanoId,
        content: DataFileContentCreate
    ): Result<DataFileContentOverview> {
        val size = dataFileContentRepository.save(nanoId, content) ?: return Result.failure(RuntimeException(""))

        return Result.success(DataFileContentOverviewDTO(size))
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