package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class CityTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "cityTags")
    private Collection<NetworkDTO> networkDTO;
    public CityTagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
