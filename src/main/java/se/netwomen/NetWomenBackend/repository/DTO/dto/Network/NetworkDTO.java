package se.netwomen.NetWomenBackend.repository.DTO.dto.Network;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NetworkDTO {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    private String city;
    private String country;
    private String networkNumber;
    // private List<String> tags = new ArrayList<>();
    protected NetworkDTO (){}

    public NetworkDTO(Long id, String name, String description, String link, String pictureUrl, String city, String country, String networkNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.city = city;
        this.country = country;
        this.networkNumber = networkNumber;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getNetworkNumber() {
        return networkNumber;
    }
}
