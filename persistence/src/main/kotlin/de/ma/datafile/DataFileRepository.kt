package de.ma.datafile

import de.ma.domain.datafile.shared.NanoId
import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository: PanacheRepositoryBase<DataFileEntity, NanoId> {
}