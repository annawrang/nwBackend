package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.List;
import java.util.Set;

public class CountryTag {

    private String name;
    private List<CityTag> cityTags;

    protected CountryTag(){}

    public CountryTag(String name, List<CityTag> cityTags) {
        this.name = name;
        this.cityTags =cityTags;
    }

    public String getName() {
        return name;
    }

    public List<CityTag> getCityTags() {
        return cityTags;
    }
}
