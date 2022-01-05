package de.ma.web.datafile

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.domain.datafile.DataFileSearchParams
import de.ma.domain.shared.PagedList
import de.ma.web.datafile.form.*
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.jboss.resteasy.reactive.MultipartForm
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/datafile")
class DataFileResource(
    private val dataFileManagementUseCase: DataFileManagementUseCase
) {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    suspend fun getDataFile(
        @Parameter(
            name = "id",
            schema = Schema(implementation = String::class)
        ) @PathParam(value = "id") dataFileSearchParams: DataFileSearchParams
    ): Response {
        val dataFileResult = dataFileManagementUseCase.getDataFile(dataFileSearchParams)
        if (dataFileResult.isFailure) {
            throw dataFileResult.exceptionOrNull()!!
        }

        val dataFileShow = dataFileResult.getOrNull()!!
        val file = dataFileShow.content.file

        val responseBuilder = Response.ok(file)
        responseBuilder.header(
            "Content-Disposition",
            "attachment;filename= ${dataFileShow.name}.${dataFileShow.extension}"
        )
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
    suspend fun create(@MultipartForm dataFileCreateForm: DataFileCreateForm): DataFileOverviewForm {
        val dataFileOverviewResult = dataFileManagementUseCase.dataFileCreate(
            dataFileCreateForm.toDataFileCreate()
        )

        if (dataFileOverviewResult.isFailure) {
            throw dataFileOverviewResult.exceptionOrNull()!!
        }

        return dataFileOverviewResult.getOrNull()!!.toImpl()
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    suspend fun deleteFile(@PathParam(value = "id") id: String) {
        val deletedResult = dataFileManagementUseCase.deleteDataFile(DeleteFormParamsDataFile(id.toNanoId()))

        if (deletedResult.isFailure) {
            throw deletedResult.exceptionOrNull()!!
        }
    }

    @GET
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFiles(): PagedList<DataFileOverviewForm> {

        val dataFilesResult = dataFileManagementUseCase.getDataFilesPaged(
            PagedParamsImpl(0, 10)
        )
        if (dataFilesResult.isFailure) {
            throw RuntimeException(dataFilesResult.exceptionOrNull()?.message ?: "")
        }

        val pagedList = dataFilesResult.getOrNull()!!

        return pagedList.pagedMap { dataFileOverview ->
            dataFileOverview.toImpl()
        }
    }

}