package de.ma.web.models

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.jboss.resteasy.reactive.PartType
import java.io.File
import java.io.InputStream
import javax.validation.constraints.NotNull
import javax.ws.rs.FormParam
import javax.ws.rs.core.MediaType

data class DataFileCreateForm(
    @get:FormParam("name")
    var name: String? = null,
    @get:FormParam("extension")
    var extension: String? = null,
    @get:FormParam("content")
    @field:PartType(MediaType.APPLICATION_OCTET_STREAM)
    @get:Schema(type = SchemaType.STRING, format = "binary", description = "The file content")
    @get:NotNull
    var content: File? = null
)

fun DataFileCreateForm.toDataFileCreate(): DataFileCreate {
    return DataFileCreateImpl(
        name = name!!,
        extension = extension!!,
        content = DataFileContentCreateImpl(
            input = content!!.inputStream(),
        ),
    )
}

data class DataFileCreateImpl(
    override val name: String,
    override val extension: String,
    override val content: DataFileContentCreate,
) : DataFileCreate


data class DataFileContentCreateImpl(
    override val input: InputStream,
) : DataFileContentCreate