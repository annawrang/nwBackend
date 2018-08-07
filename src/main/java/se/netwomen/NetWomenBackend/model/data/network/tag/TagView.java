package se.netwomen.NetWomenBackend.model.data.network.tag;

import java.util.Collection;
import java.util.List;

public class TagView {
    private List<CountryTagAlternative> countryTagAlternatives;
    private List<ForTag> forTags;

    public TagView(List<CountryTagAlternative> countryTagAlternatives, List<ForTag> forTags) {
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
