package de.ma.web.datafile

import de.ma.domain.shared.PagedList

fun <T : Any, R : Any> PagedList<T>.pagedMap(transform: (T) -> R): PagedList<R> {
    val map = this.items.map(transform)
    return PagedListImpl(this.page, this.pageCount, map)
}


data class PagedListImpl<T>(
    override val page: Int,
    override val pageCount: Int,
    override val items: List<T>
) : PagedList<T>