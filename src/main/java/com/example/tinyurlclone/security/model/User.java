package com.example.tinyurlclone.security.model;

import com.example.tinyurlclone.common.ObjectID;
import com.example.tinyurlclone.common.UID;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class User implements UserDetails {

    private com.example.tinyurlclone.user.model.User user;

    public String getId() {
        UID uid = new UID(user.getId(), ObjectID.USER);
        return uid.toString();
    }

    public com.example.tinyurlclone.user.model.User getInnerUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
