package de.ma.impl.shared

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import de.ma.impl.nanoid.NanoIdConverter
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@MappedSuperclass
abstract class AbstractBaseEntity : HasId<NanoId?>, PanacheEntityBase() {


    @get:Id
    @get:GeneratedValue(generator = "nano-generator")
    @get:GenericGenerator(
        name = "nano-generator",
        strategy = "de.ma.impl.nanoid.NanoIdGenerator"
    )
    @get:Convert(converter = NanoIdConverter::class)
    override var id: NanoId? = null
}