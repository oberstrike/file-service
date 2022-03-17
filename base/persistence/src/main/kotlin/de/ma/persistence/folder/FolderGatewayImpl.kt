package de.ma.persistence.folder

import de.ma.domain.folder.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParam
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.folder.data.toEntity
import de.ma.persistence.folder.data.toShow
import io.smallrye.mutiny.coroutines.awaitSuspending
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FolderGatewayImpl(
    private val folderRepository: FolderRepository
) : FolderGateway {

    override suspend fun createFolder(folderCreate: FolderCreate): Result<FolderShow> {
        val entity = folderCreate.toEntity()

        val persisted = try {
            folderRepository.persist(entity).awaitSuspending()
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return Result.success(persisted.toShow())
    }

    override suspend fun deleteFolder(folderSearchParams: FolderSearchParams): Result<Boolean> {
        TODO("Not yet implemented")
    }


    override suspend fun getFolderByParams(folderSearchParams: FolderSearchParams): Result<FolderShow> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFolder(folderUpdate: FolderUpdate): Result<FolderOverview> {
        TODO("Not yet implemented")
    }

    override suspend fun getFoldersPaged(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParam?
    ): Result<PagedList<FolderOverview>> {
        TODO("Not yet implemented")
    }

}