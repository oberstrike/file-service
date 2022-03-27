package de.ma.persistence.folder.data

import de.ma.domain.folder.Folder
import de.ma.domain.folder.FolderOverview
import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.shared.AbstractBaseEntity
import org.hibernate.Hibernate
import javax.persistence.*


@Entity(name = "folder")
@AttributeOverrides(
    AttributeOverride(name = "id", column = Column(name = "folder_id")),
)
class FolderEntity(
    override var name: String = "",
    @get:OneToMany(
        mappedBy = "folder",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    override var dataFiles: MutableList<DataFileEntity> = mutableListOf()
) : AbstractBaseEntity(), Folder {


    fun addDataFile(dataFile: DataFileEntity) {
        dataFiles.add(dataFile)
        dataFile.folder = this
    }


    @get:Version
    var version: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        if (other is FolderEntity) {
            return id == other.id
        }
        return false
    }

    override fun hashCode(): Int = id.hashCode()


}

data class FolderOverviewDTO(
    override val id: NanoId,
    override val name: String,
    override var size: Long
) : FolderOverview

fun FolderEntity.toShow(): FolderShow {
    return FolderShowDTO(
        id = id!!,
        name = name,
        dataFiles = emptyList(),
        size = dataFiles.size
    )
}


fun FolderEntity.toOverview(): FolderOverview {
    return FolderOverviewDTO(
        id = id!!,
        name = name,
        size = this.dataFiles.size.toLong()
    )
}