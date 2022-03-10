package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentOverview
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.nanoid.NanoId
import java.io.File
import java.io.InputStream

interface DataFileContentTest {

    fun withDataFileContentCreate(
        input: InputStream,
        block: (DataFileContentCreate) -> Unit
    )

    fun withDataFileContentOverview(
        size: Long,
        block: (DataFileContentOverview) -> Unit
    )

}
