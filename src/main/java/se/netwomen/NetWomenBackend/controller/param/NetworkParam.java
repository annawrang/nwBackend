package se.netwomen.NetWomenBackend.controller.param;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

public final class NetworkParam {

    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("size")
    @DefaultValue("10")
    private int size;
    @QueryParam("country")
    @DefaultValue("null")
    private String country;
    @QueryParam("area")
    @DefaultValue("null")
    private String area;
    @QueryParam("fortag")
    private List<String> forTags;
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

    public List<String> getForTags() {
        return forTags;
    }

    public String getSearchText() {
        return searchText;
    }
}
