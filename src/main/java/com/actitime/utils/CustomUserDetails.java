package com.actitime.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final Long id;

    public Long getId() {
        return id;
    }

    public CustomUserDetails(Long id, String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public String toJSON() {
        ObjectNode root = new ObjectMapper().createObjectNode();
        root.put("id", getId());
        ArrayNode authorities = root.putArray("authorities");
        for (GrantedAuthority authority : getAuthorities())
            authorities.add(authority.getAuthority());
        return root.toString();
    }
}
