package de.ma.datafile

import io.quarkus.hibernate.reactive.panache.PanacheEntity
import javax.persistence.Cacheable
import javax.persistence.Entity


@Entity
@Cacheable
class DataFileEntity(val name: String = "") : PanacheEntity()