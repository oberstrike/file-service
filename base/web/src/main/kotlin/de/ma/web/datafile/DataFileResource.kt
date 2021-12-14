package de.ma.web.datafile

import de.ma.datafile.api.datafile.GetDataFilesUseCase
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/datafile")
class DataFileResource(
    private val getDataFilesUseCase: GetDataFilesUseCase
) {
    @GET
    fun show(): String {
        print(getDataFilesUseCase::class.java.name)
        return "show"
    }

}