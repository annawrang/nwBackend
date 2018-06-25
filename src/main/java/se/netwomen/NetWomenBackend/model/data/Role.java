package se.netwomen.NetWomenBackend.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    @JsonProperty("role_admin")
    ROLE_ADMIN,
    @JsonProperty("role_client")
    ROLE_CLIENT;

    public String getAuthority() {
        return name();
    }
}
