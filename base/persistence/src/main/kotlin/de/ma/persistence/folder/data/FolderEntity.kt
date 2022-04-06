package de.ma.persistence.folder.data

import de.ma.domain.folder.Folder
import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.datafile.data.DataFileEntity
import de.ma.persistence.shared.AbstractBaseEntity
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.Hibernate
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.*


@Entity(name = "folder")
@AttributeOverrides(
    AttributeOverride(name = "id", column = Column(name = "folder_id")),
)
class FolderEntity(
) : AbstractBaseEntity(), Folder {

    override var name: String = ""


    @get:OneToMany(
        mappedBy = "folder",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    override var dataFiles: MutableList<DataFileEntity> = mutableListOf()

    @get:Column(name = "deleted", columnDefinition = "boolean default false")
    var deleted: Boolean = false


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

    fun addDataFile(dataFile: DataFileEntity) {
        val dataFiles = mutableListOf<DataFileEntity>()
        dataFiles.addAll(this.dataFiles)
        dataFiles.add(dataFile)
        this.dataFiles = dataFiles
        dataFile.folder = this
    }
}


suspend fun FolderEntity.toShow(): FolderShow {
    return Mutiny.fetch(dataFiles).map {
        FolderShowDTO(
            id = id!!,
            name = name,
            dataFiles = dataFiles.map { it.id?.id ?: "" },
            size = dataFiles.size
        )
    }.awaitSuspending()


}


