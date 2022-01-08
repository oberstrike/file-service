package de.ma.persistence.datafile

import de.ma.domain.nanoid.NanoId
import de.ma.persistence.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository: PanacheRepositoryBase<DataFileEntity, NanoIdEntity> {
}

fun NanoId.toEntity(): NanoIdEntity {
    return NanoIdEntity(this.value)
}
