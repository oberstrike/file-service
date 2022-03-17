package de.ma.web.apis;

import de.ma.datafile.api.management.FolderManagementUseCase
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.FolderCreate
import de.ma.domain.folder.FolderOverview
import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.web.models.DataFileCreateForm
import de.ma.web.models.PagedParamsImpl
import de.ma.web.models.toDataFileCreate
import org.jboss.resteasy.reactive.MultipartForm

import javax.ws.rs.*
import javax.ws.rs.core.Response


import javax.validation.constraints.*
import javax.validation.Valid
import javax.ws.rs.core.MediaType


@Path("/folders")
class FoldersApi(
    private val folderManagementUseCase: FolderManagementUseCase
) {

    @Path("/{id}/datafiles")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createDatafileInFolder(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId, @Valid @NotNull @MultipartForm dataFileCreateForm: DataFileCreateForm
    ): DataFileShow = handle {
        folderManagementUseCase.createDataFileInFolder(id, dataFileCreateForm.toDataFileCreate())
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createFolder(@Valid @NotNull folderCreate: FolderCreate): FolderShow = handle {
        folderManagementUseCase.createFolder(folderCreate)
    }

    @Path("/{id}/datafiles")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteDatafilesFromFolder(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ): Unit = handle {
        folderManagementUseCase.deleteDatafilesFromFolder(id)
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteFolder(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ) = handle {
        folderManagementUseCase.deleteFolder(id)
    }

    @Path("/{id}/datafiles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getDatafilesFromFolder(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId,
        @QueryParam("limit") @DefaultValue("10") limit: Int?,
        @QueryParam("page") @DefaultValue("1") page: Int?
    ): PagedList<DataFileShow> {
        return folderManagementUseCase.getDataFilesFromFolder(id, limit, page)
    }


    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFolder(
        @PathParam("id") @Pattern(regexp = "^[0-9A-z_-]{21}$") @Size(
            min = 21,
            max = 21
        ) id: NanoId
    ): FolderShow = handle {
        folderManagementUseCase.getFolderById(id)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFolders(@QueryParam("limit") limit: Int = 10,
                           @QueryParam("page") page: Int = 1,
                           @QueryParam("sort") sort: String? = null,
                           ) = handle {
        folderManagementUseCase.getFoldersPaged(pagedParams = PagedParamsImpl(page, limit))
    }

    private suspend fun <T> handle(block: suspend () -> Result<T>): T {
        return block.invoke().getOrThrow()
    }
}
