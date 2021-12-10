package de.ma.datafile.impl.utils

import de.ma.datafile.impl.CreateDataFileUseCaseImpl
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileShowView
import de.ma.domain.datafile.content.DataFileContentCreate
import de.ma.domain.datafile.content.DataFileContentOverview
import de.ma.domain.datafile.content.DataFileContentShow
import de.ma.domain.datafile.shared.*
import java.io.File
import java.io.InputStream

data class DataFileContentOverviewImpl(
    override val size: Long
) : DataFileContentOverview

data class NanoIdImpl(override val text: String) : NanoId

class NanoIdGatewayImpl : NanoIdGateway {
    override fun toText(nanoId: NanoId): String {
        return nanoId.text
    }

    override fun toNanoId(text: String): NanoId {
        return NanoIdImpl(text)
    }
}

data class DataFileDeleteImpl(
    override val id: String,
    override val version: Long
) : DataFileDelete

data class DataFileShowImpl(
    override val id: String,
    override val name: String,
    override val version: Long,
    override val size: Long,
) : DataFileShowView

data class DataFileCreateImpl(
    override val name: String,
    override val content: DataFileContentCreate
) : DataFileCreate

data class DataFileContentCreateImpl(
    override val input: InputStream
) : DataFileContentCreate


data class PagedParamsImpl(
    override val page: Int,
    override val pageSize: Int
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

data class DataFileContentShowImpl(
    override val content: File
) : DataFileContentShow

fun nanoId(text: String) = NanoIdImpl(text)

fun dataFileDelete(
    targetId: String = "123",
    targetVersion: Long = 1
): DataFileDelete = DataFileDeleteImpl(targetId, targetVersion)

fun dataFileShow(targetId: String = "123", version: Long = 1, size: Long = 1, name: String = "Name"): DataFileShowView {
    return DataFileShowImpl(targetId, name, version, size)
}


fun dataFileCreate(dataFileContentCreate: DataFileContentCreate, name: String = "") = DataFileCreateImpl(
    name, dataFileContentCreate
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
