package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId
import java.time.LocalDateTime

class DataFileTestImpl : DataFileTest {

    override fun withDataFileShow(
        content: DataFileContentShow,
        name: String,
        extension: String,
        domain: String?,
        block: (DataFileShow) -> Unit
    ) {
        return DataFileShowImpl(
            content = content,
            name = name,
            extension = extension,
            domain = domain
        ).let(block)
    }

    override fun withDataFileOverview(name: String,
                                      size: Long,
                                      id: NanoId,
                                      domain: String?,
                                      block: (DataFileOverview) -> Unit) {
        return DataFileOverviewImpl(
            name = name,
            id = id,
            createdAt = LocalDateTime.now(),
            domain = domain,
            extension = "txt",
        ).let(block)
    }

    override fun withDataFileDelete(targetId: NanoId,
                                    domain: String?,
                                    block: (DeleteParamsDataFile) -> Unit) {
        return DeleteImplParamsDataFile(
            id = targetId,
            domain = domain
        ).let(block)
    }

    override fun withDataFileCreate(
        extension: String,
        name: String,
        domain: String,
        content: DataFileContentCreate,
        block: (DataFileCreate) -> Unit
    ) {
        return DataFileCreateImpl(
            content = content,
            extension = extension,
            name = name,
            domain = domain
        ).let(block)
    }

    override fun withDataFileSearch(nanoId: NanoId,
                                    domain: String?,
                                    block: (DataFileSearchParams) -> Unit) {
        return DataFileDataFileSearchImpl(
            domain = domain,
            id = nanoId
        ).let(block)
    }



}

data class DataFileShowImpl(
    override var content: DataFileContentShow,
    override val name: String,
    override val extension: String,
    override val domain: String?
) : DataFileShow

data class DeleteImplParamsDataFile(
    override val id: NanoId,
    override val domain: String?
) : DeleteParamsDataFile

data class DataFileOverviewImpl(
    override val name: String,
    override val id: NanoId,
    override val createdAt: LocalDateTime,
    override val extension: String,
    override val domain: String?
) : DataFileOverview

data class DataFileCreateImpl(
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String,
    override val domain: String
) : DataFileCreate

data class DataFileDataFileSearchImpl(
    override val id: NanoId,
    override val domain: String?
) : DataFileSearchParams