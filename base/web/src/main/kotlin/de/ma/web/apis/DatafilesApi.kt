package de.ma.web.apis;


import de.ma.domain.datafile.DataFileCreate
import de.ma.domain.nanoid.NanoId
import javax.ws.rs.*
import javax.ws.rs.core.Response


import javax.validation.constraints.*
import javax.validation.Valid
import javax.ws.rs.core.MediaType


@Path("/datafiles")
class DatafilesApi {

    @Path("/{id}")
    @DELETE()
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteDatafile(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ): Response {
        return Response.ok().entity("magic!").build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    suspend fun downloadDatafile(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ): Response {
        return Response.ok().entity("magic!").build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getDatafileById(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ): Response {
        return Response.ok().entity("magic!").build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateDatafile(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId, @Valid @NotNull dataFileUpdate: DataFileCreate
    ): Response {
        return Response.ok().entity("magic!").build();
    }
}
