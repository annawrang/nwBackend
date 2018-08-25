package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.List;

public class TagView {
    private List<CountryTag> countryTags;
    private List<ForTag> forTags;

    public TagView(List<CountryTag> countryTags, List<ForTag> forTags) {
        this.countryTags = countryTags;
        this.forTags = forTags;
    }

    public List<CountryTag> getCountryTags() {
        return countryTags;
    }

    public List<ForTag> getForTags() {
        return forTags;
    }
}
