package de.ma.persistence.folder.data

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.shared.toPagedList
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.hibernate.reactive.mutiny.Mutiny
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FolderRepository : PanacheRepositoryBase<FolderEntity, NanoId> {


    fun findDataFilesById(id: NanoId): Uni<List<NanoId?>> {
        return findById(id.toEntity())
            .chain { folder ->
                if (folder == null) {
                    return@chain Uni.createFrom().nullItem()
                }
                Mutiny.fetch(folder.dataFiles).map { dataFile -> dataFile.map { it.id } }
            }
    }


    fun hasDataFileWithName(id: NanoId, name: String): Uni<Boolean> {
        return findById(id.toEntity())
            .chain { folderEntity ->
                Mutiny.fetch(folderEntity.dataFiles).map { dataFiles ->
                    dataFiles.any { it.name == name }
                }
            }
    }

    suspend fun getFolderPaged(pagedParams: PagedParams): Uni<PagedList<FolderEntity>>? {
        return Panache.withTransaction {
            findAll()
                .page<FolderEntity>(pagedParams.page, pagedParams.size)
                .toPagedList<FolderEntity>(pagedParams)
        }
    }

    fun getFolderById(id: NanoId): Uni<FolderEntity>? {
        return findById(id.toEntity())


    }

}