package de.ma.persistence.shared

import de.ma.persistence.shared.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.GeneratedValue
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractBaseEntity : PanacheEntityBase() {


    @get:EmbeddedId
    @get:GeneratedValue(generator = "nano-generator")
    @get:GenericGenerator(
        name = "nano-generator",
        strategy = "de.ma.persistence.shared.nanoid.NanoIdGenerator"
    )
    @get:Column(columnDefinition = "CHAR(21)")
    var id: NanoIdEntity? = null
}
