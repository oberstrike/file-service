package de.ma.domain.datafile.shared

interface PagedList<T> {
    val page: Int
    val pageCount: Int
    val items: List<T>
}

