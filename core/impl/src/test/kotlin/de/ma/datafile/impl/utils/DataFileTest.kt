package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId

interface DataFileTest {
    fun withDataFileShow(
        content: DataFileContentShow = DataFileContentShowImpl(file()),
        name: String = "test",
        extension: String = "txt",
        block: (DataFileShow) -> Unit
    )

    fun withDataFileDelete(
        targetId: NanoId = nanoId(),
        block: (DataFileDelete) -> Unit
    )

    fun withDataFileOverview(
        name: String = "",
        size: Long = 0,
        id: NanoId = nanoId(),
        block: (DataFileOverview) -> Unit
    )

    fun withDataFileCreate(
        extension: String,
        name: String,
        content: DataFileContentCreate = DataFileContentCreateImpl(inputStream()),
        block: (DataFileCreate) -> Unit
    )

    fun withDataFileSearch(
        nanoId: NanoId,
        block: (DataFileSearch) -> Unit
    )


}


