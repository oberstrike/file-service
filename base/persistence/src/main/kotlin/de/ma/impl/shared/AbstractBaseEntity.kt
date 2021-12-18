package de.ma.impl.shared

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import de.ma.impl.nanoid.NanoIdConverter
import de.ma.impl.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@MappedSuperclass
abstract class AbstractBaseEntity : PanacheEntityBase() {


    @get:EmbeddedId
    @get:GeneratedValue(generator = "nano-generator")
    @get:GenericGenerator(
        name = "nano-generator",
        strategy = "de.ma.impl.nanoid.NanoIdGenerator"
    )
    @get:Column(columnDefinition = "CHAR(21)")
    var id: NanoIdEntity? = null
}