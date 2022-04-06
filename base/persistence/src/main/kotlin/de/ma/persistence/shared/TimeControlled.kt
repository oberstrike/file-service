package de.ma.persistence.shared

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column

interface TimeControlled : Serializable {
    @get:CreationTimestamp
    @get:Column(name = "created_at")
    var createdAt: LocalDateTime

    @get:UpdateTimestamp
    @get:Column(name = "updated_at")
    var updatedAt: LocalDateTime?
}

class TimeControlledImpl : TimeControlled {
    override var createdAt: LocalDateTime = LocalDateTime.now()
    override var updatedAt: LocalDateTime? = LocalDateTime.now()
}