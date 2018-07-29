package se.netwomen.NetWomenBackend.resource.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext cres) throws IOException {
//        System.out.println("filter");
//        cres.getHeaders().add("Access-Control-Allow-Origin", "*");
//        cres.getHeaders().add("Access-Control-Allow-Headers", "origin, Content-Type, " +
//                "accept, Authorization, Auth-Token, Usernumber, Cache-Control");
//        cres.getHeaders().add("Access-Control-Expose-Headers", "Auth-Token, Usernumber");
//        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
//        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
////        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

}