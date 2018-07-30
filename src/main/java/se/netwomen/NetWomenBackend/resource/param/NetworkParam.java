package se.netwomen.NetWomenBackend.resource.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public final class NetworkParam {

    @QueryParam("page")
    @DefaultValue("3")
    private int page;
    @QueryParam("size")
    @DefaultValue("1")
    private int size;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
