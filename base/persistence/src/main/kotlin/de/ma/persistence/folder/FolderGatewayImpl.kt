package de.ma.persistence.folder

import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.folder.data.*
import de.ma.persistence.shared.PagedParamsDTO
import de.ma.persistence.shared.pagedMap
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.PanacheEntity_.id
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hibernate.reactive.mutiny.Mutiny
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional


@ApplicationScoped
class FolderGatewayImpl(
    private val folderRepository: FolderRepository,
    private val dataFileGateway: DataFileGateway
) : FolderGateway {

    @Transactional
    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow> {
        val entity = folderCreate.toEntity()

        val persisted = try {
            Panache.withTransaction {
                folderRepository.persist(entity).chain { folderEntity ->
                    Mutiny.fetch(folderEntity.dataFiles).map { dataFiles ->
                        FolderShowDTO(
                            id = folderEntity.id!!,
                            name = folderEntity.name,
                            dataFiles = dataFiles.map { it.id!!.id },
                            size = dataFiles.size
                        )
                    }
                }
            }.awaitSuspending()
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return Result.success(persisted)
    }

    @Transactional
    override suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean> {
        val id = folderSearchParams.id ?: return Result.failure(IllegalArgumentException("id is null"))

        val isDeleted = deleteDatafilesFromFolder(id).isSuccess
        if (!isDeleted) return Result.failure(IllegalArgumentException("could not delete datafiles from folder"))


        val folderEntity = folderRepository.findById(id.toEntity()).awaitSuspending()
        folderEntity.deleted = true

        return try {
            Panache.withTransaction {
                folderRepository.persist(folderEntity)
            }.awaitSuspending()
            Result.success(true)

        } catch (e: Exception) {
            return Result.failure(e)
        }

    }

    @Transactional
    override suspend fun getFolderById(id: NanoId): Result<FolderShow> {
        val folderShow = Panache.withTransaction {
            folderRepository.getFolderById(id)
        }.chain { folderEntity ->
            Mutiny.fetch(folderEntity.dataFiles).map { dataFiles ->
                FolderShowDTO(
                    id = folderEntity.id!!,
                    name = folderEntity.name,
                    dataFiles = dataFiles.map { it.id!!.id },
                    size = dataFiles.size
                )
            }
        }.awaitSuspending()

        return folderShow?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("folder not found"))
    }


    @Transactional
    override suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit> {
        dataFileGateway.deleteByFolderId(id.toEntity()).getOrElse {
            return Result.failure(it)
        }
        return Result.success(Unit)
    }

    @Transactional
    override suspend fun updateFolder(
        folderUpdate: FolderUpdate,
        folderId: NanoId
    ): Result<FolderShow> {

        val entity = folderRepository.findById(folderId.toEntity()).awaitSuspending()
            ?: return Result.failure(IllegalArgumentException("id is null"))

        if (entity.version != folderUpdate.version) {
            return Result.failure(IllegalArgumentException("version is not correct"))
        }

        entity.name = folderUpdate.name

        return try {
            Result.success(folderRepository.persist(entity).awaitSuspending().toShow())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun oneFolderWithSameNameExists(name: String): Boolean {
        return folderRepository.find("name = :name", mapOf("name" to name)).count().awaitSuspending() > 0
    }

    override suspend fun hasDataFileWithNameById(name: String, folderId: NanoId): Boolean {
        return Panache.withTransaction {
            folderRepository.hasDataFileWithName(folderId, name)
        }.awaitSuspending()
    }

    override suspend fun existsById(id: NanoId): Boolean {
        return folderRepository.findById(id.toEntity()).awaitSuspending() != null
    }

    override suspend fun getDataFilesPaged(
        folderId: NanoId,
        pagedParams: PagedParams
    ): Result<PagedList<DataFileShow>> {
        return Result.failure(IllegalArgumentException("not implemented"))
    }

    @Transactional
    override suspend fun getFoldersPaged(
        pagedParams: PagedParams?,
        folderSearchParams: FolderSearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderShow>> = withContext(Dispatchers.IO) {
        val result = folderRepository.getFolderPaged(pagedParams ?: PagedParamsDTO(1, 10))?.awaitSuspending()
            ?: return@withContext Result.failure(IllegalArgumentException("paged list is null"))

        return@withContext Result.success(result.pagedMap<FolderEntity, FolderShow> { folderEntity ->
            withContext(Dispatchers.IO) {
                folderEntity.toShow()
            }
        })

    }

}


fun <T> T.toResult(): Result<T> {
    return Result.success(this)
}