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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.southwinds.interlink.conf.Config;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class Jwt {
    private final Config cfg;

    // HMAC using SHA-256 - The JWT signature algorithm used to sign the token
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // or HS384 or HS512

    public Jwt(Config cfg) {
        this.cfg = cfg;
    }

    //Sample method to construct a JWT
    public String createJWT(String id, String issuer, String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(cfg.getJwtSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    /**
     * check the passed in date is not in the past
     * @param tokenExpirationDate
     * @return
     */
    public boolean hasExpired(Date tokenExpirationDate) {
        // token expired if now is after the date in the token
        return getNow().after(tokenExpirationDate);
    }

    //Sample method to validate and read the JWT
    public Claims parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(cfg.getJwtSecret())).build()
                .parseClaimsJws(jwt).getBody();
    }

    public String newSecret() {
        SecretKey key = Keys.secretKeyFor(signatureAlgorithm); 
        return Encoders.BASE64.encode(key.getEncoded());
    }

    private Date getNow() {
        long nowMillis = System.currentTimeMillis();
        return new Date(nowMillis);
    }
}
