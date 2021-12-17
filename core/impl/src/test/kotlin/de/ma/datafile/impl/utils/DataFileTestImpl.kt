package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId

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

    override fun withDataFileOverview(name: String, size: Long, id: NanoId, block: (DataFileOverview) -> Unit) {
        return DataFileOverviewImpl(
            name = name,
            size = size,
            id = id
        ).let(block)
    }

    override fun withDataFileDelete(targetId: NanoId, block: (DataFileDelete) -> Unit) {
        return DataFileDeleteImpl(
            id = targetId
        ).let(block)
    }

    override fun withDataFileCreate(
        extension: String,
        name: String,
        content: DataFileContentCreate,
        block: (DataFileCreate) -> Unit
    ) {
        return DataFileCreateImpl(
            content = content,
            extension = extension,
            name = name
        ).let(block)
    }

    override fun withDataFileSearch(nanoId: NanoId, block: (DataFileSearch) -> Unit) {
        return DataFileSearchImpl(
            id = nanoId
        ).let(block)
    }

}

data class DataFileShowImpl(
    override var content: DataFileContentShow,
    override val name: String,
    override val extension: String
) : DataFileShow

data class DataFileDeleteImpl(
    override val id: NanoId
) : DataFileDelete

data class DataFileOverviewImpl(
    override val name: String,
    override val size: Long,
    override val id: NanoId
) : DataFileOverview

data class DataFileCreateImpl(
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String
) : DataFileCreate

data class DataFileSearchImpl(
    override val id: NanoId
) : DataFileSearch