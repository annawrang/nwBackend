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
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CountryTagDTO> countryTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<CityTagDTO> cityTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ForTagDTO> forTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<OfferTagDTO> offerTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TypeTagDTO> typeTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<OtherTagDTO> otherTags;
    private String networkNumber;
    // private List<String> tags = new ArrayList<>();
    protected NetworkDTO (){}

    public NetworkDTO(Long id, String name, String description, String link, String pictureUrl, Set<CountryTagDTO> countryTags, Set<CityTagDTO> cityTags, Set<ForTagDTO> forTags, Set<OfferTagDTO> offerTags, Set<TypeTagDTO> typeTags, Set<OtherTagDTO> otherTags, String networkNumber) {
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

    public Set<CountryTagDTO> getCountryTags() {
        return countryTags;
    }

    public Set<CityTagDTO> getCityTags() {
        return cityTags;
    }

    public Set<ForTagDTO> getForTags() {
        return forTags;
    }

    public Set<OfferTagDTO> getOfferTags() {
        return offerTags;
    }

    public Set<TypeTagDTO> getTypeTags() {
        return typeTags;
    }

    public Set<OtherTagDTO> getOtherTags() {
        return otherTags;
    }

    public String getNetworkNumber() {
        return networkNumber;
    }
}
