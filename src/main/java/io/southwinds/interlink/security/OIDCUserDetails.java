/*
    Interlink Configuration Management Database
    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Contributors to this project, hereby assign copyright in their code to the
    project, to be licensed under the same terms as the rest of the code.
*/

package io.southwinds.interlink.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.*;

/*
    Stores user information including access token claims.
 */
public class OIDCUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private OAuth2AccessToken token;
    private List<GrantedAuthority> authorities;

    public OIDCUserDetails(
            Map<String, String> authenticationInfo,
            Map<String, String> authorisationInfo,
            OAuth2AccessToken token
    ) {
        this.userId = authenticationInfo.get("sub");
        this.username = authenticationInfo.get("email");
        this.authorities = extractAuthorities(authorisationInfo);
        this.token = token;
    }

    /*
        When using grant-type=authorization_code then it extracts the 'roles' claim
        and populates the authorities
    */
    private static List<GrantedAuthority> extractAuthorities(Map<String, String> authorisationInfo) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role = authorisationInfo.get("role");
        if (role != null) {
            String[] parts = role.split(",");
            for (String part : parts) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + part.trim()));
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OAuth2AccessToken getToken() {
        return token;
    }

    public void setToken(OAuth2AccessToken token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return null;
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