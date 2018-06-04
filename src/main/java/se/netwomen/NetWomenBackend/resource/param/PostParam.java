package se.netwomen.NetWomenBackend.resource.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class PostParam {

    @QueryParam("page")
    public Integer page;

    @QueryParam("numbersPerPage") @DefaultValue("10")
    public Integer numbersPerPage;
}
