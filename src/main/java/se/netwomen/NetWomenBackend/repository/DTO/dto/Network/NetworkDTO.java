package se.netwomen.NetWomenBackend.repository.DTO.dto.Network;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class NetworkDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    @ManyToMany

    private Collection<CountryTagDTO> countryTags;
    @ManyToMany

    private Collection<CityTagDTO> cityTags;
    @ManyToMany

    private Collection<ForTagDTO> forTags;
    @ManyToMany

    private Collection<OfferTagDTO> offerTags;
    @ManyToMany
    private Collection<TypeTagDTO> typeTags;
    @ManyToMany
    private Collection<OtherTagDTO> otherTags;
    private String networkNumber;
    // private List<String> tags = new ArrayList<>();
    protected NetworkDTO (){}

    public NetworkDTO(Long id, String name, String description, String link, String pictureUrl, Collection<CountryTagDTO> countryTags, Collection<CityTagDTO> cityTags, Collection<ForTagDTO> forTags, Collection<OfferTagDTO> offerTags, Collection<TypeTagDTO> typeTags, Collection<OtherTagDTO> otherTags, String networkNumber) {
        this.id = id;
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
        this.networkNumber = networkNumber;
    }

    public Long getId() {
        return id;
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

    public Collection<CountryTagDTO> getCountryTags() {
        return countryTags;
    }

    public Collection<CityTagDTO> getCityTags() {
        return cityTags;
    }

    public Collection<ForTagDTO> getForTags() {
        return forTags;
    }

    public Collection<OfferTagDTO> getOfferTags() {
        return offerTags;
    }

    public Collection<TypeTagDTO> getTypeTags() {
        return typeTags;
    }

    public Collection<OtherTagDTO> getOtherTags() {
        return otherTags;
    }

    public String getNetworkNumber() {
        return networkNumber;
    }
}
