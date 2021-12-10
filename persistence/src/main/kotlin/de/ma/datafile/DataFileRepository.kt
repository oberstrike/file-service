package de.ma.datafile

import io.quarkus.hibernate.orm.panache.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class DataFileRepository: PanacheRepository<DataFileEntity> {
}