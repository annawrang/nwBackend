package se.netwomen.NetWomenBackend.model.data.network.tag.alternative;

public class TagUpdateAlternative {
    private String countryTag;
    private String areaTag;

    protected TagUpdateAlternative(){}

    public TagUpdateAlternative(String countryTag, String areaTag) {
        this.countryTag = countryTag;
        this.areaTag = areaTag;
    }

    public String getCountryTag() {
        return countryTag;
    }

    public String getAreaTag() {
        return areaTag;
    }
}
