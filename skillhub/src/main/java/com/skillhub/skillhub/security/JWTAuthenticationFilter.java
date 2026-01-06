package com.skillhub.skillhub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{

    @Value("${HEADER}")
    private String HEADER;
    @Value("${SECRET}")
    private String SECRET;

    private final JWTBuilder jwtBuilder;

    public JWTAuthenticationFilter(JWTBuilder jwtBuilder) {
        this.jwtBuilder = jwtBuilder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(checkJWTToken(request)){
            Claims claims = validateToken(request);
            if(claims.get("aut") != null){
                setAuthentication(claims);
            }else{
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    private Claims validateToken(HttpServletRequest request){
        String token = request.getHeader(HEADER).replace(jwtBuilder.getPREFIX()+" ", "");
        return Jwts.parser().verifyWith(getKey(SECRET)).build().parseSignedClaims(token).getPayload();
    }

    private void setAuthentication(Claims claims) {
        List<String> authorities = (List<String>) claims.get("aut");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private SecretKey getKey(String SECRET){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HEADER);
        return authHeader != null && authHeader.startsWith(jwtBuilder.getPREFIX());
    }
}
