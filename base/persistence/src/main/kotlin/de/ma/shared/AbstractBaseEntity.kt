package de.ma.shared

import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.HasId
import de.ma.nanoid.NanoIdEntity
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.GeneratedValue
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractBaseEntity : HasId<NanoIdEntity?>, PanacheEntityBase() {

    @get:EmbeddedId
    @get:GeneratedValue(generator = "nano-generator")
    @get:GenericGenerator(
        name = "nano-generator",
        strategy = "de.ma.ikarus.persistence.shared.nanoid.NanoIdGenerator"
    )
    @get:Column(columnDefinition = "CHAR(21)")
    override var id: NanoIdEntity? = null
}