package com.nextgendevs.hanchor.security;

import com.nextgendevs.hanchor.io.entity.AuthorityEntity;
import com.nextgendevs.hanchor.io.entity.RolesEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class UserPrinciple implements UserDetails {

    UserEntity userEntity;
    private final String userName;
    private String userId;

    public UserPrinciple(UserEntity userEntity, String userName) {
        this.userEntity = userEntity;
        this.userName = userName;
        userId = userEntity.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        Collection<RolesEntity> roles = userEntity.getRoles();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();

        if (roles == null) return  authorities;

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach(authorityEntity -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return userName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
