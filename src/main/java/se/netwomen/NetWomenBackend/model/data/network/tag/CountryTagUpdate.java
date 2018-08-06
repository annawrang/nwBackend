package se.netwomen.NetWomenBackend.model.data.network.tag;

public class CountryTagUpdate {
    private String countryTag;
    private String cityTag;

    protected CountryTagUpdate(){}

    public CountryTagUpdate(String countryTag, String cityTag) {
        this.countryTag = countryTag;
        this.cityTag = cityTag;
    }

    public String getCountryTag() {
        return countryTag;
    }

    public String getCityTag() {
        return cityTag;
    }
}
