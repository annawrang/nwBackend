package se.netwomen.NetWomenBackend.resource.mapper;

import com.sun.jndi.cosnaming.ExceptionMapper;
import se.netwomen.NetWomenBackend.service.exceptions.InvalidCookieException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
public final class InvalidCookieMapper implements javax.ws.rs.ext.ExceptionMapper<InvalidCookieException> {
    @Override
    public Response toResponse(InvalidCookieException exception) {
        return Response.status(BAD_REQUEST).entity(singletonMap("Error", exception.getMessage())).build();
    }
}
