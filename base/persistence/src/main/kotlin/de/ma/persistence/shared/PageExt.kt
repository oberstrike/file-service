package de.ma.persistence.shared

import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.panache.common.Page
import io.smallrye.mutiny.coroutines.awaitSuspending

suspend inline fun <T : Any> PanacheQuery<T>.toPagedList(pagedParams: PagedParams): PagedList<T> {
    val targetPage = this.page<T>(Page.of(pagedParams.page, pagedParams.size))
    return PagedListImpl(
        pagedParams.page,
        targetPage.pageCount().awaitSuspending(),
        targetPage.list<T>().awaitSuspending()
    )
}

fun <T : Any, R : Any> PagedList<T>.pagedMap(transform: (T) -> R): PagedList<R> {
    val map = this.items.map(transform)
    return PagedListImpl(this.page, this.pageCount, map)
}


data class PagedListImpl<T>(
    override val page: Int,
    override val pageCount: Int,
    override val items: List<T>
) : PagedList<T>
