package de.ma.persistence.shared

import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.panache.common.Page
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.hibernate.reactive.mutiny.Mutiny

 fun <T : Any> PanacheQuery<T>.toPagedList(pagedParams: PagedParams): Uni<PagedList<T>> {
    val targetPage = this.page<T>(Page.of(pagedParams.page, pagedParams.size))
    return targetPage.pageCount().chain { pageCount ->
        Mutiny.fetch(targetPage.list<T>()).chain { uniList ->
            uniList.chain { list ->
                Uni.createFrom().item(PagedListImpl(pagedParams.page, pageCount, list))
            }
        }
    }
}

suspend fun <T : Any, R : Any> PagedList<T>.pagedMap(transform: suspend (T) -> R): PagedList<R> {
    val map = this.items.asFlow().map(transform)
    return PagedListImpl(this.page, this.pageCount, map.toList())
}


data class PagedListImpl<T>(
    override val page: Int,
    override val pageCount: Int,
    override val items: List<T>
) : PagedList<T>
