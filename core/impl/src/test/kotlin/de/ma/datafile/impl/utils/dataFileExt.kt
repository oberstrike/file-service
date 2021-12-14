package de.ma.datafile.impl.utils

import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.nanoid.NanoIdGateway
import de.ma.domain.shared.*
import java.io.File
import java.io.InputStream

data class DataFileContentOverviewImpl(
    override val size: Long
) : DataFileContentOverview

data class NanoIdImpl(override val value: String) : NanoId

class NanoIdGatewayImpl : NanoIdGateway {
    override fun toText(nanoId: NanoId): String {
        return nanoId.value
    }

    override fun toNanoId(text: String): NanoId {
        return NanoIdImpl(text)
    }
}

data class DataFileDeleteImpl(
    override val id: String,
    override val version: Long
) : DataFileDelete

data class DataFileOverviewImpl(
    override val name: String,
    override val size: Long,
    override val id: String
) : DataFileOverview

data class DataFileCreateImpl(
    override val name: String,
    override val extension: String,
    override val content: DataFileContentCreate
) : DataFileCreate

data class DataFileContentCreateImpl(
    override val input: InputStream
) : DataFileContentCreate


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


data class DataFileShowImpl(
    override val content: DataFileContentShow,
    override val name: String,
    override val extension: String
): DataFileShow

data class DataFileContentShowImpl(
    override val file: File
) : DataFileContentShow

fun nanoId(text: String) = NanoIdImpl(text)

fun dataFileDelete(
    targetId: String = "123",
    targetVersion: Long = 1
): DataFileDelete = DataFileDeleteImpl(targetId, targetVersion)

fun dataFileOverview(
     name: String,
     size: Long,
     id: String
): DataFileOverview {
    return DataFileOverviewImpl(name, size, id)
}


fun dataFileCreate(dataFileContentCreate: DataFileContentCreate,
                   extension: String = "txt",
                   name: String = "") = DataFileCreateImpl(
    name, extension, dataFileContentCreate
)

fun pagedParams() = PagedParamsImpl(1, 10)

fun searchParams() = SearchParamsImpl("", "")

fun <T> pagedList() = PagedListImpl<T>(listOf(), 1, 1)

inline fun <reified T> T.file(): File = File(this!!::class.java.getResource("/files/TestDatei-Klein.txt")!!.toURI())

inline fun <reified T> T.inputStream() = this!!::class.java.getResource("/files/TestDatei-Klein.txt")?.openStream()
    ?: throw IllegalStateException("Test file not found")

fun dataFileContentOverview(size: Long) = DataFileContentOverviewImpl(size)

fun dataFileContentCreate(input: InputStream): DataFileContentCreate = DataFileContentCreateImpl(input)

fun dataFileContentShow(content: File) = DataFileContentShowImpl(content)

fun dataFileShow(content: DataFileContentShow, name: String, extension: String) = DataFileShowImpl(content, name, extension)