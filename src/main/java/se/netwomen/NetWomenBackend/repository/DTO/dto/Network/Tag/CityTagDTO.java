package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class CityTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "cityTagDTOs")
    private Set<CountryTagDTO> countryTagDTOs;

    protected CityTagDTO() {
    }

    public CityTagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Set<CountryTagDTO> getCountryTagDTOs() {
        return countryTagDTOs;
    }
}
