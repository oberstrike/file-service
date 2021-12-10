package de.ma.datafile

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import javax.persistence.Entity


@Entity
class DataFileEntity(val name: String = "") : PanacheEntity()