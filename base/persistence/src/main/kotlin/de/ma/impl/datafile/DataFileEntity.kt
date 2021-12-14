package de.ma.impl.datafile

import de.ma.domain.datafile.DataFile
import de.ma.domain.nanoid.NanoId
import de.ma.impl.shared.AbstractBaseEntity
import org.hibernate.Hibernate
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.Table


@Entity(name = "data_file")
@Table(name = "data_file")
@Cacheable
data class DataFileEntity(
    override var name: String,
    override var extension: String
) : AbstractBaseEntity(), DataFile<NanoId> {

    override var size: Long = 0

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