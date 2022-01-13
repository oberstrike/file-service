package de.ma.persistence.utils.sql

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Sql(
    val value: Array<String>
)
