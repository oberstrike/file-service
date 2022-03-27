package de.ma.web.models

import de.ma.domain.content.DataFileContentCreate
import de.ma.domain.datafile.DataFileCreate
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.jboss.resteasy.reactive.PartType
import org.jboss.resteasy.reactive.multipart.FileUpload
import java.io.InputStream
import javax.validation.constraints.NotNull
import javax.ws.rs.FormParam
import javax.ws.rs.core.MediaType
import kotlin.io.path.inputStream

class DataFileCreateForm{
    @FormParam("name")
    @NotNull
    var name: String? = null

    @FormParam("extension")
    @NotNull
    var extension: String? = null

    @FormParam("content")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @Schema(type = SchemaType.STRING, format = "binary", description = "The file content")
    @NotNull
    var content: FileUpload? = null
}

fun DataFileCreateForm.toDataFileCreate(): DataFileCreate {
    return DataFileCreateImpl(
        name = name!!,
        extension = extension!!,
        content = DataFileContentCreateImpl(
            input = content!!.uploadedFile().inputStream(),
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