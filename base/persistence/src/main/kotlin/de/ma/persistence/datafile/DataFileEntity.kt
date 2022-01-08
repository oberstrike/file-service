package de.ma.persistence.datafile

import de.ma.domain.datafile.DataFile
import de.ma.persistence.shared.AbstractBaseEntity
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "data_file")
@Table(name = "data_file")
@AttributeOverrides(
    value = [
        AttributeOverride(name ="value", column = Column(name = "data_file_id")),
    ]
)
data class DataFileEntity(
    override var name: String,
    override var extension: String,
    @get:Column(name = "domain", columnDefinition = "varchar(255)")
    override var domain: String = ""
) : AbstractBaseEntity(), DataFile {

    @get:CreationTimestamp
    @get:Column(name = "created_at")
    override var createdAt: LocalDateTime = LocalDateTime.now()

    @get:Column(name = "deleted")
    var deleted: Boolean = false


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val that = other as DataFileEntity?
        return id == that!!.id
    }


    override fun hashCode(): Int = id.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }

}
