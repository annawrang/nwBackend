package se.netwomen.NetWomenBackend.repository.DTO.dto.Network;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.*;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import javax.persistence.*;
import java.util.Objects;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CountryTagDTO> countryTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ForTagDTO> forTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<OfferTagDTO> offerTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<TypeTagDTO> typeTags;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<OtherTagDTO> otherTags;
    private String networkNumber;
    @ManyToMany(mappedBy = "networkDTOs")
    private Set<UserDTO> users;
    // private List<String> tags = new ArrayList<>();
    protected NetworkDTO (){}

    public NetworkDTO(Long id, String name, String description, String link, String pictureUrl, Set<CountryTagDTO> countryTags, Set<ForTagDTO> forTags, Set<OfferTagDTO> offerTags, Set<TypeTagDTO> typeTags, Set<OtherTagDTO> otherTags, String networkNumber) {
        this.id = id;
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

    public Set<UserDTO> getUsers() {
        return users;
    }

}
