package se.netwomen.NetWomenBackend.resource.mapper;

import se.netwomen.NetWomenBackend.service.exceptions.CustomException;
import se.netwomen.NetWomenBackend.service.exceptions.InvalidCookieException;

import javax.ws.rs.core.Response;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class CustomExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException exception) {
        return Response.status(BAD_REQUEST).entity(singletonMap("Error", exception.getMessage())).build();
    }
}
