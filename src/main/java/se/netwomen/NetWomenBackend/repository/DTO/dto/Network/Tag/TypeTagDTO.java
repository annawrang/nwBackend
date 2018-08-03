package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class TypeTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "typeTags")
    private Collection<NetworkDTO> networkDTO;
    protected TypeTagDTO() {
    }
    public TypeTagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
