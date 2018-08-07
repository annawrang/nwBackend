package se.netwomen.NetWomenBackend.model.data.network.tag;

public class AlternativeTagUpdate {
    private String countryTag;
    private String areaTag;

    protected AlternativeTagUpdate(){}

    public AlternativeTagUpdate(String countryTag, String areaTag) {
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
