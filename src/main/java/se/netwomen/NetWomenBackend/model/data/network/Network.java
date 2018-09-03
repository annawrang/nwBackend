package se.netwomen.NetWomenBackend.model.data.network;

import se.netwomen.NetWomenBackend.model.data.network.tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import java.util.Objects;
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
    private boolean myNetwork;


    protected  Network(){}

    public Network(String name, String description, String link, String pictureUrl, Set<CountryTag> countryTags, Set<ForTag> forTags, String networkNumber, boolean myNetwork) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.countryTags = countryTags;
        this.forTags = forTags;
        this.myNetwork = myNetwork;
        this.offerTags = offerTags;
        this.typeTags = typeTags;
        this.otherTags = otherTags;
        this.networkNumber = networkNumber;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Network network = (Network) o;
        return Objects.equals(name, network.name) &&
                Objects.equals(description, network.description) &&
                Objects.equals(link, network.link) &&
                Objects.equals(pictureUrl, network.pictureUrl) &&
                Objects.equals(networkNumber, network.networkNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, link, pictureUrl, networkNumber);
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


    public String getNetworkNumber() {
        return networkNumber;
    }

    public boolean isMyNetwork() {
        return myNetwork;
    }

    public void setMyNetwork(boolean myNetwork) {
        this.myNetwork = myNetwork;
    }
}
