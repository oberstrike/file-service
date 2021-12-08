package de.ma.datafile.utils

import de.ma.datafile.DataFileCreate
import de.ma.datafile.DataFileDelete
import de.ma.datafile.DataFileShowView
import de.ma.shared.PagedList
import de.ma.shared.PagedParams
import de.ma.shared.SearchParams
import java.io.InputStream

fun dataFileDelete(
    targetId: String = "123",
    targetVersion: Long = 1
): DataFileDelete {
    return object : DataFileDelete {
        override val id: String
            get() = targetId
        override val version: Long
            get() = targetVersion
    }
}

fun dataFileShow(id: String = "123", version: Long = 1, size: Long = 1, name: String = "Name"): DataFileShowView {
    return object : DataFileShowView {
        override val id: String
            get() = id
        override val version: Long
            get() = version
        override val size: Long
            get() = size
        override val name: String
            get() = name
    }
}


fun dataFileCreate(inputStream: InputStream, name: String = "") = object : DataFileCreate {
    override val name: String
        get() = name
    override val inputStream: InputStream
        get() = inputStream
}

fun pagedParams(): PagedParams {
    return object : PagedParams {
        override val pageSize: Int
            get() = 1
        override val page: Int
            get() = 0
    }
}

fun searchParams(): SearchParams {
    return object : SearchParams {
        override val field: String
            get() = "id"
        override val content: String
            get() = ""
    }
}

fun pagedList() = object : PagedList<DataFileShowView> {
    override val items: List<DataFileShowView>
        get() = emptyList()
    override val pageCount: Int
        get() = 0
    override val page: Int
        get() = 0

    //equals and hashCode based on items, pageCount and page
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PagedList<*>

        if (items != other.items) return false
        if (pageCount != other.pageCount) return false
        if (page != other.page) return false
        return true
    }

    override fun hashCode(): Int {
        var result = items.hashCode()
        result = 31 * result + pageCount
        result = 31 * result + page
        return result
    }
}
