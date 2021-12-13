package de.ma.web.datafile

import de.ma.datafile.api.datafile.ShowDataFilesUseCase
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/datafile")
class DataFileResource(
    private val showDataFilesUseCase: ShowDataFilesUseCase
) {
    @GET
    fun show(): String {
        print(showDataFilesUseCase::class.java.name)
        return "show"
    }

}