package de.ma.domain.folder

interface FolderCreate {
    fun toParams(): FolderSearchParams

    val name: String
}
