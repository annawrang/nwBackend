package se.netwomen.NetWomenBackend.resource.filter;

import org.springframework.util.MultiValueMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

public class CORSResponseFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext responseContext) throws IOException {
//        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
//
//        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");

    }
}
