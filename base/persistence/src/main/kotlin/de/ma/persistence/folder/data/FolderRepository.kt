package de.ma.persistence.folder.data

import de.ma.domain.nanoid.NanoId
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FolderRepository : PanacheRepositoryBase<FolderEntity, NanoId> {

    suspend fun findByName(name: String): FolderEntity? {
        return find("name", name).firstResult<FolderEntity>().awaitSuspending()
    }


    fun getFolderById(id: NanoId): Uni<FolderShowDTO>? {
         return find("id", id).firstResult<FolderEntity>()
            .map { entity ->
                val ids = entity.dataFiles.map { it.id!!.id }
                FolderShowDTO(
                    id = id,
                    name = entity.name,
                    dataFiles = ids,
                    size = ids.size
                )
            }

    }

}