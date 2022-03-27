package de.ma.persistence.folder.data

import de.ma.domain.folder.FolderCreate
import de.ma.domain.folder.FolderSearchParams

data class FolderCreateDTO(override val name: String) : FolderCreate {
    override fun toParams(): FolderSearchParams {
        return FolderSearchParamsDTO(id = null, name = name)
    }
}


fun FolderCreate.toEntity(): FolderEntity {
    return FolderEntity(name = name)
}