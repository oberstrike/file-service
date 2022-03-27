package de.ma.persistence.folder

import de.ma.domain.folder.*
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SortParam
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.datafile.data.DataFileShowDTO
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.datafile.data.toShow
import de.ma.persistence.folder.data.*
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.runBlocking
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional

@ApplicationScoped
class FolderGatewayImpl(
    private val folderRepository: FolderRepository
) : FolderGateway {

    @Transactional
    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderOverview> {
        val entity = folderCreate.toEntity()

        val persisted = try {
            Panache.withTransaction {
                folderRepository.persist(entity)
            }.awaitSuspending()
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return Result.success(persisted.toOverview())
    }

    @Transactional
    override suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean> {
        val id = folderSearchParams.id ?: return Result.failure(IllegalArgumentException("id is null"))

        val deleted = folderRepository.delete("folder_id = :id", mapOf("id" to id.id)).awaitSuspending()

        return if (deleted == 1L) {
            Result.success(true)
        } else {
            Result.success(false)
        }
    }


    @Transactional
    override suspend fun getFolderById(id: NanoId): Result<FolderShow> {
        val folderShow = Panache.withTransaction {
            folderRepository.getFolderById(id)
        }.awaitSuspending()

//        return folderShow?.let { Result.success(it) } ?: Result.failure(IllegalArgumentException("folder not found"))
        return Result.failure(IllegalArgumentException("folder not found"))

    }

    override suspend fun getFoldersPaged(
        pagedParams: PagedParams?,
        folderSearchParams: FolderSearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderOverview>> {
        TODO("Not yet implemented")
    }

    @Transactional
    override suspend fun deleteDatafilesFromFolder(id: NanoId): Result<Unit> {
        return try {
            folderRepository.findById(id.toEntity()).awaitSuspending()?.let {
                it.dataFiles.clear()
                folderRepository.persist(it).awaitSuspending()
                Result.success(Unit)
            } ?: Result.failure(IllegalArgumentException("id is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    @Transactional
    override suspend fun addDataFileToFolder(folderId: NanoId, dataFileId: NanoId): Result<Boolean> {
        folderRepository.findById(folderId.toEntity()).awaitSuspending()?.let {
            it.addDataFile(DataFileEntity(it).apply { this.id = dataFileId.toEntity() })
            folderRepository.persist(it).awaitSuspending()
            return Result.success(true)
        }

        return Result.failure(IllegalArgumentException("id is null"))
    }

    @Transactional
    override suspend fun updateFolder(
        folderUpdate: FolderUpdate,
        folderId: NanoId
    ): Result<FolderOverview> {

        val entity = folderRepository.findById(folderId.toEntity()).awaitSuspending()
            ?: return Result.failure(IllegalArgumentException("id is null"))

        if (entity.version != folderUpdate.version) {
            return Result.failure(IllegalArgumentException("version is not correct"))
        }

        entity.name = folderUpdate.name

        return try {
            Result.success(folderRepository.persist(entity).awaitSuspending().toOverview())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun exists(name: String): Boolean {
        return folderRepository.find("name = :name", mapOf("name" to name)).count().awaitSuspending() > 0
    }

    override suspend fun hasDataFileWithNameById(name: String, folderId: NanoId): Boolean {
        return privateHasDataFileWithNameById(name, folderId).awaitSuspending()
    }

    private fun privateHasDataFileWithNameById(name: String, folderId: NanoId): Uni<Boolean> {
        return Panache.withTransaction {
             folderRepository.findById(folderId.toEntity()).map { folderEntity ->
                 folderEntity.dataFiles.any { it.name == name }
             }
        }
    }
}
