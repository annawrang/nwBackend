package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity

public class CountryTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "countryTags")
    private Collection<NetworkDTO> networkDTO;
    public CountryTagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
