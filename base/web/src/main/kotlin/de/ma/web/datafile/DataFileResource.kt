package de.ma.web.datafile

import de.ma.datafile.api.management.DataFileManagement
import de.ma.domain.datafile.DataFileOverview
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.shared.PagedList
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.jboss.resteasy.reactive.MultipartForm
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/datafile")
class DataFileResource(
    private val dataFileManagement: DataFileManagement
) {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    suspend fun getDataFile(@PathParam(value = "id") id: String): Response {
        val dataFileResult = dataFileManagement.getDataFile(DataFileSearchImpl(id.toNanoId()))
        if (dataFileResult.isFailure) {
            throw dataFileResult.exceptionOrNull()!!
        }

        val dataFileShow = dataFileResult.getOrNull()!!
        val file = dataFileShow.content.file

        val responseBuilder = Response.ok(file)
        responseBuilder.header("Content-Disposition", "attachment;filename= ${dataFileShow.name}.${dataFileShow.extension}")
        return responseBuilder.build()
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

    @GET
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFiles(): PagedList<DataFileOverviewImpl> {

        val dataFilesResult = dataFileManagement.getDataFiles(
            PagedParamsImpl(0, 10)
        )
        if (dataFilesResult.isFailure) {
            throw RuntimeException(dataFilesResult.exceptionOrNull()?.message ?: "")
        }

        val pagedList = dataFilesResult.getOrNull()!!

        return pagedList.pagedMap {
            it.toImpl()
        }
    }

}