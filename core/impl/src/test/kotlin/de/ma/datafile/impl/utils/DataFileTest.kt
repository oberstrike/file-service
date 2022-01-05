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
        domain: String?,
        block: (DataFileShow) -> Unit
    )

    fun withDataFileDelete(
        targetId: NanoId = nanoId(),
        domain: String?,
        block: (DeleteParamsDataFile) -> Unit
    )

    fun withDataFileOverview(
        name: String = "",
        size: Long = 0,
        id: NanoId = nanoId(),
        domain: String?,
        block: (DataFileOverview) -> Unit
    )

    fun withDataFileCreate(
        extension: String,
        name: String,
        domain: String,
        content: DataFileContentCreate,
        block: (DataFileCreate) -> Unit

    )

    fun withDataFileSearch(
        nanoId: NanoId,
        domain: String?,
        block: (DataFileSearchParams) -> Unit
    )


}


