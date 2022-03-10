package de.ma.datafile.impl.utils

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.content.DataFileContentShow
import de.ma.domain.datafile.*
import de.ma.domain.nanoid.NanoId

interface DataFileTest {



    fun withDataFileCreate(
        extension: String,
        name: String,
        domain: String,
        content: DataFileContentCreate,
        block: (DataFileCreate) -> Unit

    )

}


