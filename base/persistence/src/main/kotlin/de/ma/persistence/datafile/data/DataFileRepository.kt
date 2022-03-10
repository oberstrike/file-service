package de.ma.persistence.datafile.data

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.SearchParams
import de.ma.persistence.shared.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheQuery
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Sort
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository : PanacheRepositoryBase<DataFileEntity, NanoIdEntity> {
    fun find(sort: Sort?, searchParams: SearchParams?): PanacheQuery<DataFileEntity> {

        if (sort != null && searchParams != null)
            return find(searchParams.field, searchParams.content, sort)
        if (sort != null)
            return findAll(sort)
        if (searchParams != null)
            return find(searchParams.field, searchParams.content)
        return findAll()
    }
}

fun NanoId.toEntity(): NanoIdEntity {
    return NanoIdEntity(this.id)
}
