package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity

public class CountryTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "countryTags")
    private Set<NetworkDTO> networkDTO;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CityTagDTO> cityTagDTOs;

    protected CountryTagDTO() {
    }
    public CountryTagDTO(Long id, String name,  Set<CityTagDTO> cityTagDTOs) {
        this.id = id;
        this.name = name;
        this.cityTagDTOs= cityTagDTOs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<NetworkDTO> getNetworkDTO() {
        return networkDTO;
    }

    public Set<CityTagDTO> getCityTagDTOs() {
        return cityTagDTOs;
    }

}
