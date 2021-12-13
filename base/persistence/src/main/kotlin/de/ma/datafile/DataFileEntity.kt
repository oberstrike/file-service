package de.ma.datafile

import de.ma.domain.datafile.DataFile
import de.ma.nanoid.NanoIdEntity
import de.ma.shared.AbstractBaseEntity
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.Table


@Entity("data_file")
@Table(name = "data_file")
@Cacheable
data class DataFileEntity(
    override var name: String,
    override var size: Long,
    override var extension: String
) : AbstractBaseEntity(), DataFile<NanoIdEntity>