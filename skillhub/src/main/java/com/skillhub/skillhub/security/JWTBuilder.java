package com.skillhub.skillhub.security;

import lombok.Getter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Getter
public class JWTBuilder {
    @Value("${PREFIX}")
    private String PREFIX;
    @Value("${SECRET}")
    private String SECRET;
    @Value("${ISSUER}")
    private String ISSUER;

    private SecretKey key;

    public String getToken(Integer id, String email){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
        Map<String, Object> claims = Map.of("aut", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String token = Jwts
                .builder()
                .id(id.toString())
                .subject(email)
                .issuer(ISSUER)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1800000))
                .signWith(key)
                .compact();
        return "Bearer " + token;
    }
}