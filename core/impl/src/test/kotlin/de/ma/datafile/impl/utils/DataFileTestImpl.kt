package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileDelete
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileShow

class DataFileTestImpl : DataFileTest {

    override fun withDataFileShow(
        content: DataFileContentShow,
        name: String,
        extension: String,
        block: (DataFileShow) -> Unit
    ) {
        return DataFileShowImpl(
            content = content,
            name = name,
            extension = extension
        ).let(block)
    }

    override fun withDataFileOverview(name: String, size: Long, id: String, block: (DataFileOverview) -> Unit) {
        return DataFileOverviewImpl(
            name = name,
            size = size,
            id = id
        ).let(block)
    }

    override fun withDataFileDelete(targetId: String, targetVersion: Long, block: (DataFileDelete) -> Unit) {
        return DataFileDeleteImpl(
            id = targetId,
            version = targetVersion
        ).let(block)
    }

    override fun withDataFileCreate(
        content: DataFileContentCreate,
        extension: String,
        name: String,
        block: (DataFileCreate) -> Unit
    ) {
        return DataFileCreateImpl(
            content = content,
            extension = extension,
            name = name
        ).let(block)
    }

}

data class DataFileShowImpl(
    override val content: DataFileContentShow,
    override val name: String,
    override val extension: String
) : DataFileShow

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
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String
) : DataFileCreate
