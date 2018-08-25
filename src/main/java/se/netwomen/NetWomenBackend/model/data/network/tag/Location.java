package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.Set;

public final class Location {
    private String countryName;
    private Set<String> areaNames;

    protected Location(){}

    public Location(String countryName, Set<String> areaNames) {
        this.countryName = countryName;
        this.areaNames = areaNames;
    }

    public String getCountryName() {
        return countryName;
    }

    public Set<String> getAreaNames() {
        return areaNames;
    }
}
