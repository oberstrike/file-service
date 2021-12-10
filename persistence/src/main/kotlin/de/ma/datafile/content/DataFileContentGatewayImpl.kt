package de.ma.datafile.content

import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentGateway
import de.ma.domain.datafile.content.DataFileContentShow
import de.ma.domain.datafile.shared.NanoId
import java.io.File
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileContentGatewayImpl(
    private val dataFileContentRepository: DataFileContentRepository
) : DataFileContentGateway {

    override fun getContentByNanoId(nanoId: NanoId): Result<DataFileContentShow> {
        val file: File = dataFileContentRepository.findByNanoId(nanoId)
            ?: //TODO implement own exception class
            return Result.failure(RuntimeException("File not found"))

        return Result.success(DataFileContentDTO(file))
    }

    override fun saveContentByNanoId(nanoId: NanoId, content: DataFileContentCreate) {
        dataFileContentRepository.save(nanoId, content)
    }

    override fun deleteContentByNanoId(nanoId: NanoId): Boolean {
        return dataFileContentRepository.exists(nanoId)
    }


}