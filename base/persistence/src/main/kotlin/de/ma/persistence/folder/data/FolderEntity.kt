package de.ma.persistence.folder.data

import de.ma.domain.folder.Folder
import de.ma.domain.folder.FolderShow
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.datafile.data.toShow
import de.ma.persistence.shared.AbstractBaseEntity
import org.hibernate.Hibernate
import javax.persistence.*


@Entity(name = "folder")
@AttributeOverrides(
    AttributeOverride(name = "id", column = Column(name = "folder_id")),
)
class FolderEntity(
    override var size: Long,
    override var name: String,
    @get:OneToMany(
        mappedBy = "folder",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    override var dataFiles: MutableList<DataFileEntity> = mutableListOf()
) : AbstractBaseEntity(), Folder {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        if (other is FolderEntity) {
            return id == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()

    @Override
    override fun toString(): String {
        return "FolderEntity(id=$id, size=$size, dataFiles=$dataFiles, name='$name')"
    }

}

fun FolderEntity.toShow(): FolderShow {
    return FolderShowDTO(
        id = id!!,
        name = name,
        dataFiles = dataFiles.map { it.toShow() },
        size = size
    )
}
