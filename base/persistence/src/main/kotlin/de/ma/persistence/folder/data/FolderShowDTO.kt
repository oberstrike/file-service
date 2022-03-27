package de.ma.persistence.folder.data

import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class FolderShowDTO(
    override val id: NanoId,
    override val name: String,
    override val dataFiles: List<String>,
    override val size: Int
): FolderShow