package de.ma.domain.content

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import java.io.InputStream

interface DataFileContentCreate {
    val input: InputStream
}
