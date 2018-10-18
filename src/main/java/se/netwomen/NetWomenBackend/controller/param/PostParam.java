package se.netwomen.NetWomenBackend.controller.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class PostParam {

    @QueryParam("page") @DefaultValue("1")
    public Integer page;

    @QueryParam("numbersPerPage") @DefaultValue("10")
    public Integer numbersPerPage;
}
