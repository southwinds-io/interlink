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

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
    When sending a straight bearer token based on grant-type=password then it extracts the 'roles' claim
    and populates the authorities
    NOTE: not ideal that claim extraction logic for grant_type=password sits in a different place than
     for grant_type=authorization_code, but it is the only way (I found so far) to get the Spring Security
     framework to read the claim for both  grant types.
     For the login for the grant_type=authorization_code see @see io.southwinds.interlink.security.OIDCUserDetails
 */
public class JwtTokenClaimsConverter extends JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        Object rolesObj = jwt.getClaims().get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (rolesObj != null) {
            String[] roles = rolesObj.toString().split(",");
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.trim()));
            }
        }
        return authorities;
    }
}

