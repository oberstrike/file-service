package de.ma.impl.datafile

import de.ma.domain.datafile.DataFile
import de.ma.domain.nanoid.NanoId
import de.ma.impl.nanoid.NanoIdEntity
import de.ma.impl.shared.AbstractBaseEntity
import org.hibernate.Hibernate
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
    override var extension: String
) : AbstractBaseEntity(), DataFile<NanoIdEntity> {

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