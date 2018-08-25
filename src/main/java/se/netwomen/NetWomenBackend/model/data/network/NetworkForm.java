package se.netwomen.NetWomenBackend.model.data.network;

import se.netwomen.NetWomenBackend.model.data.network.tag.ForTag;
import se.netwomen.NetWomenBackend.model.data.network.tag.Location;

import java.util.Set;

public final class NetworkForm {
    private String name;
    private String description;
    private Set<Location> locations;
    private Set<ForTag> forTags;
    private String link;
    private String pictureUrl;

    protected NetworkForm (){}
    public NetworkForm(String name, String description, Set<Location> locations, Set<ForTag> forTags, String link, String pictureUrl) {
        this.name = name;
        this.description = description;
        this.locations = locations;
        this.forTags = forTags;
        this.link = link;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public Set<ForTag> getForTags() {
        return forTags;
    }

    public String getLink() {
        return link;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
