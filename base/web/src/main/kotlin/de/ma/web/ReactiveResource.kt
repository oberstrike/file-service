package de.ma.web

import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("/vertx")
class ReactiveResource {

    @GET
    suspend fun get(): String {
        return "Hello from Kotlin"
    }

}