package de.ma.persistence.folder.data

import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId

data class FolderShowDTO(
    override val id: NanoId,
    override val name: String,
    override val dataFiles: List<DataFileShow>,
    override val size: Long
): FolderShow