package de.ma.persistence.folder.data

import de.ma.domain.nanoid.NanoId
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FolderRepository : PanacheRepositoryBase<FolderEntity, NanoId> {

    suspend fun findByName(name: String): FolderEntity? {
        return find("name", name).firstResult<FolderEntity>().awaitSuspending()
    }

}