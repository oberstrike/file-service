package de.ma.web.apis;


import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.domain.nanoid.NanoId
import de.ma.web.models.DataFileSearchParamsImpl
import de.ma.web.models.DataFileUpdateForm
import de.ma.web.models.DeleteDataFileParamsImpl
import de.ma.web.validators.IsNanoId
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import javax.ws.rs.*


import javax.ws.rs.core.MediaType


@Path("/datafiles")
class DataFilesApi(
    private val dataFileManagementUseCase: DataFileManagementUseCase
) {

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteDatafile(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId
    ) = dataFileManagementUseCase.deleteDataFile(DeleteDataFileParamsImpl(id))


    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    suspend fun downloadDatafile(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId
    ) = handle {
        dataFileManagementUseCase.getDataFile(DataFileSearchParamsImpl(id = id))
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(
        content = [
            Content(
                mediaType = MediaType.APPLICATION_JSON,
                schema = Schema(implementation = DataFileUpdateForm::class)
            )
        ]
    )
    suspend fun updateDatafile(
        @PathParam("id") @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId,
        dataFileUpdate: DataFileUpdateForm
    ) = handle {
        dataFileManagementUseCase.updateDataFile(dataFileUpdate.toDataFileUpdate())
    }

    private suspend fun <T> handle(block: suspend () -> Result<T>): T {
        return block.invoke().getOrThrow()
    }
}
