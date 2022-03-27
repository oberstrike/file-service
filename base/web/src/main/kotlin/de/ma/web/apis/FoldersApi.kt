package de.ma.web.apis;


import de.ma.datafile.api.management.FolderManagementUseCase
import de.ma.domain.datafile.DataFileShow
import de.ma.domain.folder.FolderOverview
import de.ma.domain.folder.FolderShow
import de.ma.domain.nanoid.NanoId
import de.ma.domain.shared.PagedList
import de.ma.web.models.*
import de.ma.web.validators.IsNanoId
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.jboss.resteasy.reactive.MultipartForm
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@Path("/folders")
class FoldersApi(
    private val folderManagementUseCase: FolderManagementUseCase
) {

    @Path("/{id}/datafiles")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequestBody(
        content = [
            Content(
                mediaType = MediaType.MULTIPART_FORM_DATA,
                schema = Schema(implementation = DataFileCreateForm::class)
            )
        ]
    )
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createDatafileInFolder(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId, @MultipartForm dataFileCreateForm: DataFileCreateForm
    ): DataFileShow = handle {
        folderManagementUseCase.createDataFileInFolder(id, dataFileCreateForm.toDataFileCreate())
    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(
        content = [
            Content(
                mediaType = MediaType.APPLICATION_JSON,
                schema = Schema(implementation = FolderCreateParams::class)
            )
        ]
    )
    suspend fun createFolder(folderCreate: FolderCreateParams): FolderOverview {
        return handle {
            folderManagementUseCase.createFolder(folderCreate)
        }
    }

    @Path("/{id}/datafiles")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteDatafilesFromFolder(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId
    ): Unit = handle {
        folderManagementUseCase.deleteDatafilesFromFolder(id)
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteFolder(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId
    ) = handle {
        folderManagementUseCase.deleteFolder(id)
    }

    @Path("/{id}/datafiles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getDatafilesFromFolder(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId,
        @BeanParam pagedParamsForm: PagedParamsForm
    ): PagedList<DataFileShow> = handle {
        folderManagementUseCase.getDataFilesFromFolder(id, pagedParamsForm.toPagedParams())
    }


    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFolder(
        @PathParam("id") @IsNanoId @Parameter(
            name = "id", schema = Schema(
                implementation = String::class
            )
        ) id: NanoId
    ): FolderShow = handle {
        folderManagementUseCase.getFolderById(id)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getFolders(
        @BeanParam pagedParamsForm: PagedParamsForm,
        @QueryParam("sort") sort: String? = null
    ) = handle {
        folderManagementUseCase.getFoldersPaged(pagedParams = pagedParamsForm.toPagedParams())
    }

    private suspend fun <T> handle(block: suspend () -> Result<T>): T {
        return block.invoke().getOrThrow()
    }
}
