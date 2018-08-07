package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import javax.persistence.*;
import java.util.Set;
@Entity
public class CountryTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AreaTagDTO> areaTagDTOs;
    ;

    protected CountryTagDTO() {
    }

    public CountryTagDTO(Long id, String name, Set<AreaTagDTO> areaTagDTOs) {
        this.id = id;
        this.name = name;
        this.areaTagDTOs = areaTagDTOs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AreaTagDTO> getAreaTagDTOs() {
        return areaTagDTOs;
    }
}
