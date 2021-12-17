package de.ma.impl.shared

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@MappedSuperclass
abstract class AbstractBaseEntity : HasId<NanoId?>, PanacheEntityBase() {


    @get:EmbeddedId
    @get:GeneratedValue(generator = "nano-generator")
    @get:GenericGenerator(
        name = "nano-generator",
        strategy = "de.ma.impl.nanoid.NanoIdGenerator"
    )
    @get:Column(columnDefinition = "CHAR(21)")
    override var id: NanoId? = null
}