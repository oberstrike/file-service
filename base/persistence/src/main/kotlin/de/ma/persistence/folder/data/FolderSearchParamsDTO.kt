package de.ma.persistence.folder.data

import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.nanoid.NanoId

data class FolderSearchParamsDTO(
    override val id: NanoId?,
    override val name: String?
): FolderSearchParams