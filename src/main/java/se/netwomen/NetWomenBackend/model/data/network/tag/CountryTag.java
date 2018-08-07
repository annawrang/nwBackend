package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.List;

public class CountryTag {

    private String name;
    private List<AreaTag> areaTags;

    protected  CountryTag() {}

    public CountryTag(String name, List<AreaTag> areaTags) {
        this.name = name;
        this.areaTags = areaTags;
    }

    public String getName() {
        return name;
    }

    public List<AreaTag> getAreaTags() {
        return areaTags;
    }
}
