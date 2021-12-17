package de.ma.web.datafile

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.jboss.resteasy.reactive.PartType
import java.io.File
import java.io.InputStream
import javax.ws.rs.FormParam
import javax.ws.rs.core.MediaType

class DataFileCreateForm {

    @FormParam("name")
    var name: String? = null

    @FormParam("extension")
    var extension: String? = null

    @FormParam("content")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @Schema(type = SchemaType.STRING, format = "binary", description = "file data")
    var content: File? = null

    override fun toString(): String {
        return "DataFileCreateForm(name=$name, extension=$extension, content=${content?.name})"
    }
}

fun DataFileCreateForm.toDataFileCreate(): DataFileCreate {
    return DataFileCreateImpl(
        name = name!!,
        extension = extension!!,
        content = DataFileContentCreateImpl(
            input = content!!.inputStream()
        )
    )
}

data class DataFileCreateImpl(
    override val name: String,
    override val extension: String,
    override val content: DataFileContentCreate
) : DataFileCreate


data class DataFileContentCreateImpl(
    override val input: InputStream
) : DataFileContentCreate