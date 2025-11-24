package com.lesley.GestionPresences.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTservice {

    //clé secrete pour signer les tokens
    private static final String SECRET = "9RqJAndfg5eqvEsVQJ1fjuXIxUnmfdSTFTqui0INlsHfl0DocV0grzfNGzPo6BVFPaAvR2HzQmPxdpAOlSBjiuCDnfxT2OtfbU7p00mIhC2yw9SrxLYqNT1Cu6UhW7HB9RqJAndfg5eqvEsVQJ1fjuXIxUnmfdSTFTqui0INlsHfl0DocV0grzfNGzPo6BVFPaAvR2HzQmPxdpAOlSBjiuCDnfxT2OtfbU7p00mIhC2yw9SrxLYqNT1Cu6UhW7HB";

    // ✅ Convertir la clé secrète en clé utilisable
    private Key getSingInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ✅ Décoder le token et extraire toutes les claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //extraire un champ spécifique du token
    public <T> T extractClaim(String token, Function<Claims , T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //extraire le nom d'utlisateur
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Générer un token JWT sans claims additionnels
    public  String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),
                userDetails);
    }

    //générer un token JWT avec claims additionne
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .signWith(getSingInKey(),SignatureAlgorithm.HS512)
                .compact();
    }

    // ✅ Extraire la date d’expiration du token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //vérifier si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //vérifier si le token est valide
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
