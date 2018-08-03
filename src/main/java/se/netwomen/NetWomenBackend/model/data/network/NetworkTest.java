package se.netwomen.NetWomenBackend.model.data.network;

import se.netwomen.NetWomenBackend.model.data.network.tag.*;

import java.util.Set;

public class NetworkTest {

    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    private Set<CountryTag> countryTags;
    private Set<CityTag> cityTags;
    private Set<ForTag> forTags;
    private Set<OfferTag> offerTags;
    private Set<TypeTag> typeTags;
    private Set<OtherTag> otherTags;

    protected  NetworkTest(){}

    public NetworkTest(String name, String description, String link, String pictureUrl, Set<CountryTag> countryTags, Set<CityTag> cityTags, Set<ForTag> forTags, Set<OfferTag> offerTags, Set<TypeTag> typeTags, Set<OtherTag> otherTags) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.countryTags = countryTags;
        this.cityTags = cityTags;
        this.forTags = forTags;
        this.offerTags = offerTags;
        this.typeTags = typeTags;
        this.otherTags = otherTags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public Set<CountryTag> getCountryTags() {
        return countryTags;
    }

    public Set<CityTag> getCityTags() {
        return cityTags;
    }

    public Set<ForTag> getForTags() {
        return forTags;
    }

    public Set<OfferTag> getOfferTags() {
        return offerTags;
    }

    public Set<TypeTag> getTypeTags() {
        return typeTags;
    }

    public Set<OtherTag> getOtherTags() {
        return otherTags;
    }
}
