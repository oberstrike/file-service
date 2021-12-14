package de.ma.impl.datafile

import de.ma.domain.nanoid.NanoId
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository: PanacheRepositoryBase<DataFileEntity, NanoId> {
}