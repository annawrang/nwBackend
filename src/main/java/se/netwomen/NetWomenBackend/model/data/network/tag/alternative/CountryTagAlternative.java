package se.netwomen.NetWomenBackend.model.data.network.tag.alternative;

import java.util.List;

public class CountryTagAlternative {

    private String name;
    private List<AreaTagAlternative> areaTagAlternatives;

    protected CountryTagAlternative(){}

    public CountryTagAlternative(String name, List<AreaTagAlternative> areaTagAlternatives) {
        this.name = name;
        this.areaTagAlternatives =areaTagAlternatives;
    }

    public String getName() {
        return name;
    }

    public List<AreaTagAlternative> getCityTags() {
        return areaTagAlternatives;
    }
}
