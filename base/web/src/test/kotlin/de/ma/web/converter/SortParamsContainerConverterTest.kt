package de.ma.web.converter

import de.ma.web.models.SortParamsContainer
import de.ma.web.models.SortParamsImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SortParamsContainerConverterTest {

    private val sortParamsContainerConverter = SortParamsContainerConverter()

    @Test
    fun testToString() {
        val result = sortParamsContainerConverter.toString(
            SortParamsContainer(
                listOf(
                    SortParamsImpl("name", "asc"),
                    SortParamsImpl("age", "desc")
                )
            )
        )

        println(result)
    }

    @Test
    fun fromString() {
        val result = sortParamsContainerConverter.fromString(
            "name,asc;age,desc"
        )

        println(result)
    }
}