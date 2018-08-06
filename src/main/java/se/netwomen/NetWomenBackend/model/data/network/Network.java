package se.netwomen.NetWomenBackend.model.data.network;

import se.netwomen.NetWomenBackend.model.data.network.tag.*;

import java.util.Set;

public final class Network {

    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    private Set<CountryTag> countryTags;
    private Set<ForTag> forTags;
    private Set<OfferTag> offerTags;
    private Set<TypeTag> typeTags;
    private Set<OtherTag> otherTags;
    private String networkNumber;

    protected  Network(){}

    public Network(String name, String description, String link, String pictureUrl, Set<CountryTag> countryTags, Set<ForTag> forTags, Set<OfferTag> offerTags, Set<TypeTag> typeTags, Set<OtherTag> otherTags, String networkNumber) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.countryTags = countryTags;
        this.forTags = forTags;
        this.offerTags = offerTags;
        this.typeTags = typeTags;
        this.otherTags = otherTags;
        this.networkNumber = networkNumber;
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

    public String getNetworkNumber() {
        return networkNumber;
    }
}
