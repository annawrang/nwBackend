package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.Collection;

public class TagView {
    private Collection<CountryTag> countryTags;
    private Collection<CityTag> cityTags;
    private Collection<ForTag> forTags;

    public TagView(Collection<CountryTag> countryTags, Collection<CityTag> cityTags, Collection<ForTag> forTags) {
        this.countryTags = countryTags;
        this.cityTags = cityTags;
        this.forTags = forTags;
    }

    public Collection<CountryTag> getCountryTags() {
        return countryTags;
    }

    public Collection<CityTag> getCityTags() {
        return cityTags;
    }

    public Collection<ForTag> getForTags() {
        return forTags;
    }
}
