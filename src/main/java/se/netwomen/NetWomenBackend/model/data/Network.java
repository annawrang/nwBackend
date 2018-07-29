package se.netwomen.NetWomenBackend.model.data;

public final class Network {

    private String name;
    private String description;
    private String link;
    private String pictureUrl;
    private String city;
    private String country;
    private String networkNumber;

    protected Network (){
    }

    public Network(String name, String description, String link, String pictureUrl, String city, String country, String networkNumber) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.pictureUrl = pictureUrl;
        this.city = city;
        this.country = country;
        this.networkNumber = networkNumber;
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
