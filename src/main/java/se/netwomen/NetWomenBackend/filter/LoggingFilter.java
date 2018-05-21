package se.netwomen.NetWomenBackend.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Provider
public class LoggingFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String log = String.format("Absolute path: %s\nMethod: %s\n",
                context.getUriInfo().getAbsolutePath(),
                context.getRequest().getMethod());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.print(log + time.format(formatter));
    }
}
