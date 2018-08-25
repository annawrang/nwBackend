package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag.alternative;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class AreaTagAlternativeDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected AreaTagAlternativeDTO() {
    }

    public AreaTagAlternativeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
