package de.ma.domain.datafile

import de.ma.domain.nanoid.NanoId


//Delete a data file by id
interface DeleteDataFileParams  {
    val id: NanoId
}
