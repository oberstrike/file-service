package de.ma.datafile.impl.utils

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import java.io.File
import java.util.*


data class NanoIdImpl(override var value: String) : NanoId

data class PagedParamsImpl(
    override val page: Int,
    override val size: Int
) : PagedParams

data class SearchParamsImpl(
    override val content: String,
    override val field: String
) : SearchParams

data class PagedListImpl<T>(
    override val items: List<T>,
    override val page: Int,
    override val pageCount: Int
) : PagedList<T>

fun nanoId(text: String = UUID.randomUUID().toString()) : NanoId = NanoIdImpl(text)

fun pagedParams() = PagedParamsImpl(1, 10)

fun searchParams() = SearchParamsImpl("", "")

fun <T> pagedList() = PagedListImpl<T>(listOf(), 1, 1)

inline fun <reified T> T.file(): File = File(this!!::class.java.getResource("/files/TestDatei-Klein.txt")!!.toURI())

inline fun <reified T> T.inputStream() = this!!::class.java.getResource("/files/TestDatei-Klein.txt")?.openStream()
    ?: throw IllegalStateException("Test file not found")


