package de.ma.shared

interface PagedList<T: Any> {
    val page: Int
    val pageCount: Int
    val items: List<T>
}

