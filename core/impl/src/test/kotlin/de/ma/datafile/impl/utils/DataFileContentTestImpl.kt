package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File
import java.io.InputStream

class DataFileContentTestImpl : DataFileContentTest {

    override fun withDataFileContentShow(file: File, block: (DataFileContentShow) -> Unit) {
        DataFileContentShowImpl(file).apply(block)
    }

    override fun withDataFileContentOverview(size: Long, block: (DataFileContentOverview) -> Unit) {
        DataFileContentOverviewImpl(size).apply(block)
    }

    override fun withDataFileContentCreate(
        input: InputStream,
        id: NanoId,
        block: (DataFileContentCreate) -> Unit
    ) {
        DataFileContentCreateImpl(input, id).apply(block)
    }

}


data class DataFileContentShowImpl(
    override val file: File
) : DataFileContentShow

data class DataFileContentCreateImpl(
    override val input: InputStream,
    override val id: NanoId
) : DataFileContentCreate

data class DataFileContentOverviewImpl(
    override val size: Long
) : DataFileContentOverview

