package se.netwomen.NetWomenBackend.resource.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

public final class NetworkParam {

    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("size")
    @DefaultValue("18")
    private int size;
    @QueryParam("country")
    @DefaultValue("null")
    private String country;
    @QueryParam("area")
    @DefaultValue("null")
    private String area;
    @QueryParam("fortag")
    @DefaultValue("null")
    private String forTags;
    @QueryParam("search")
    private String searchText;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getCountry() {
        return country;
    }

    public String getArea() {
        return area;
    }

    public String getForTag() {
        return forTags;
    }

    public String getSearchText() {
        return searchText;
    }
}
