package de.ma.domain.content

import java.io.InputStream

interface DataFileContentCreate : DataFileContentSearch {
    val input: InputStream
}
