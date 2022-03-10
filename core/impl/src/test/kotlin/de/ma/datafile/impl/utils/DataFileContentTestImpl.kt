package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File
import java.io.InputStream

class DataFileContentTestImpl : DataFileContentTest {

    override fun withDataFileContentOverview(size: Long, block: (DataFileContentOverview) -> Unit) {
        DataFileContentOverviewImpl(size).apply(block)
    }

    override fun withDataFileContentCreate(
        input: InputStream,
        block: (DataFileContentCreate) -> Unit
    ) {
        DataFileContentCreateImpl(input).apply(block)
    }


}

data class DataFileContentCreateImpl(
    override val input: InputStream
) : DataFileContentCreate

data class DataFileContentOverviewImpl(
    override val size: Long
) : DataFileContentOverview

