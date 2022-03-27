package de.ma.web.models

import de.ma.domain.folder.FolderCreate
import de.ma.domain.folder.FolderSearchParams
import de.ma.domain.nanoid.NanoId

data class FolderCreateParams(
    override val name: String = "",
) : FolderCreate {
    override fun toParams(): FolderSearchParams {
        return FolderSearchParamsImpl(name)
    }
}

data class FolderSearchParamsImpl(
    override val name: String?,
    override val id: NanoId? = null
) : FolderSearchParams 


