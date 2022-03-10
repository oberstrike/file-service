package de.ma.persistence.folder.data

import de.ma.domain.folder.Folder
import de.ma.domain.folder.FolderCreate
import de.ma.domain.folder.FolderSearchParams

data class FolderCreateDTO(override val name: String): FolderCreate {
    override fun toParams(): FolderSearchParams {
        return FolderSearchParamsDTO(name = name, id = null)
    }
}


fun FolderCreate.toEntity(): FolderEntity{
    return FolderEntity(name = name, size = 0)
}