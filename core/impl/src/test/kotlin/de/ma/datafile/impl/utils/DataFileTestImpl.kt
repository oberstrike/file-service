package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId
import java.time.LocalDateTime

class DataFileTestImpl : DataFileTest {



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





}


data class DataFileCreateImpl(
    override val content: DataFileContentCreate,
    override val extension: String,
    override val name: String,
    override val domain: String
) : DataFileCreate
