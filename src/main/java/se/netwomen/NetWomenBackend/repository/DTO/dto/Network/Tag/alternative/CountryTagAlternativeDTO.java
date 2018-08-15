package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CountryTagAlternativeDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AreaTagAlternativeDTO> areaTagAlternativeDTOs;

    protected CountryTagAlternativeDTO() { }

    public CountryTagAlternativeDTO(Long id, String name, Set<AreaTagAlternativeDTO> areaTagAlternativeDTOs) {
        this.id = id;
        this.name = name;
        this.areaTagAlternativeDTOs = areaTagAlternativeDTOs;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<AreaTagAlternativeDTO> getAreaTagAlternativeDTOs() {
        return areaTagAlternativeDTOs;
    }
}
