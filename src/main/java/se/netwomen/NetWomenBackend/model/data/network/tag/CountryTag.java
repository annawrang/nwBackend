package se.netwomen.NetWomenBackend.model.data.network.tag;

public class CountryTag {

    private String name;

    protected CountryTag(){}

    public CountryTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
