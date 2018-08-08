package se.netwomen.NetWomenBackend.model.data.network.tag.alternative;

import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;

import java.util.List;

public class TagViewAlternative {
    private List<CountryTagAlternative> countryTagAlternatives;
    private List<ForTag> forTags;

    public TagViewAlternative(List<CountryTagAlternative> countryTagAlternatives, List<ForTag> forTags) {
        this.countryTagAlternatives = countryTagAlternatives;
        this.forTags = forTags;
    }

    public List<CountryTagAlternative> getCountryTags() {
        return countryTagAlternatives;
    }

    public List<ForTag> getForTags() {
        return forTags;
    }
}
