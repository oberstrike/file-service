package de.ma.persistence.datafile.data

import de.ma.domain.datafile.DataFile
import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.Folder
import de.ma.persistence.folder.data.FolderEntity
import de.ma.persistence.shared.AbstractBaseEntity
import de.ma.persistence.shared.TimeControlled
import de.ma.persistence.shared.TimeControlledImpl
import de.ma.persistence.shared.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheEntity_.id
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@AttributeOverrides(
    AttributeOverride(name = "id", column = Column(name = "data_file_id")),
)
class DataFileEntity(id: NanoIdEntity? = null) : AbstractBaseEntity(id),
    DataFile, TimeControlled by TimeControlledImpl() {

    override var name: String = ""

    override var extension: String = ""

    @get:ManyToOne(fetch = FetchType.LAZY, optional = false)
    override var folder: FolderEntity? = null

    @get:Column(name = "deleted")
    var deleted: Boolean = false

    @get:Column(name = "version")
    @get:Version
    var version: Long = 0


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val that = other as DataFileEntity?
        return id == that!!.id
    }

    override fun hashCode(): Int = id.hashCode()

}

fun DataFileCreate.toEntity(): DataFileEntity {
    val dataFileEntity = DataFileEntity()
    dataFileEntity.name = name
    dataFileEntity.extension = extension
    dataFileEntity.deleted = false
    return dataFileEntity
}

fun DataFileEntity.toShow(): DataFileShow {
    return DataFileShowDTO(
        extension = extension,
        name = name,
        id = id!!
    )
}