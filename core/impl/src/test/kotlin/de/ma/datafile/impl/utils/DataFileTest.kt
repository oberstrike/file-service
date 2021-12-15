package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileShow

interface DataFileTest {
    fun withDataFileShow(
        content: DataFileContentShow = DataFileContentShowImpl(file()),
        name: String = "test",
        extension: String = "txt",
        block: (DataFileShow) -> Unit
    )

    fun withDataFileDelete(
        targetId: String = "123",
        targetVersion: Long = 1,
        block: (DataFileDelete) -> Unit
    )

    fun withDataFileOverview(
        name: String = "",
        size: Long = 0,
        id: String = "",
        block: (DataFileOverview) -> Unit
    )

    fun withDataFileCreate(
        extension: String,
        name: String,
        content: DataFileContentCreate = DataFileContentCreateImpl(inputStream(), nanoId()),
        block: (DataFileCreate) -> Unit
    )


}


