package se.netwomen.NetWomenBackend.repository.DTO.dto.Network.Tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AreaTagDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected AreaTagDTO() {
    }

    public AreaTagDTO(Long id, String name) {
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
