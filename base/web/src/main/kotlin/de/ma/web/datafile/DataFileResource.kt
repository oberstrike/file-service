package de.ma.web.datafile

import de.ma.datafile.api.management.DataFileManagement
import io.smallrye.common.annotation.Blocking
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.jboss.resteasy.reactive.MultipartForm
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/datafile")
class DataFileResource(
    private val dataFileManagement: DataFileManagement
) {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun show(): String {
        return "show"
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequestBody(
        content = [Content(
            mediaType = MediaType.MULTIPART_FORM_DATA,
            schema = Schema(implementation = DataFileCreateForm::class)
        )]
    )
    suspend fun create(@MultipartForm dataFileCreateForm: DataFileCreateForm) {
        println(dataFileCreateForm)
        dataFileManagement.createDataFile(
            dataFileCreateForm.toDataFileCreate()
        )
    }

}