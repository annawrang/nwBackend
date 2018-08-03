package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import se.netwomen.NetWomenBackend.repository.DTO.dto.Network.NetworkDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class OtherTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "otherTags")
    private Collection<NetworkDTO> networkDTO;
    protected OtherTagDTO() {
    }
    public OtherTagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
