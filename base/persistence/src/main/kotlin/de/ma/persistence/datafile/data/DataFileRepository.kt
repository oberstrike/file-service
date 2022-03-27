package de.ma.persistence.datafile.data

import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.nanoid.NanoId
import de.ma.persistence.shared.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Sort
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository : PanacheRepositoryBase<DataFileEntity, NanoIdEntity> {
    fun find(sort: Sort?, searchParams: DataFileSearchParams?): PanacheQuery<DataFileEntity> {
        if(searchParams?.id != null) {
            return find("id = :id", mapOf("id" to searchParams.id))
        }

        if (sort != null)
            return findAll(sort)
        return findAll()
    }
}

fun NanoId.toEntity(): NanoIdEntity {
    return NanoIdEntity(this.id)
}
