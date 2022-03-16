package de.ma.web

import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider


@Provider
class CustomExceptionHandler : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.message).build()
    }
}
