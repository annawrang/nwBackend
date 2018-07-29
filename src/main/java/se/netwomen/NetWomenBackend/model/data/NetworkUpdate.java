package se.netwomen.NetWomenBackend.model.data;

public class NetworkUpdate {
    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    private String city;
    private String country;

    public NetworkUpdate(String name, String description, String link, String pictureUrl, String city, String country) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.city = city;
        this.country = country;
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
}
