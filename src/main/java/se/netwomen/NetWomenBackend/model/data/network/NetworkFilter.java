package se.netwomen.NetWomenBackend.model.data.network;

import java.util.List;

public final class NetworkFilter {
    private String placeholder;
    private String pictureUrl;
    private String networkNumber;
    private String filterType;

    public NetworkFilter(String placeholder, String pictureUrl, String networkNumber, String filterType) {
        this.placeholder = placeholder;
        this.pictureUrl = pictureUrl;
        this.networkNumber = networkNumber;
        this.filterType = filterType;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getNetworkNumber() {
        return networkNumber;
    }

    public String getFilterType() {
        return filterType;
    }
}