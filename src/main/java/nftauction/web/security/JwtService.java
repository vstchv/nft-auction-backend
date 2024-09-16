package nftauction.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nftauction.web.security.common.CustomUserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String createAuthToken(CustomUserDetails CustomUserDetails) {
    return generateToken(new HashMap<>(), CustomUserDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, CustomUserDetails CustomUserDetails) {
    return buildToken(extraClaims, CustomUserDetails, jwtExpiration);
  }

  private String buildToken(Map<String, Object> extraClaims, CustomUserDetails CustomUserDetails,
                            long expiration) {
    return Jwts.builder()
               .setClaims(extraClaims)
               .setSubject(CustomUserDetails.getUsername())
               .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + expiration))
               .signWith(getSignInKey(), SignatureAlgorithm.HS256)
               .compact();
  }

  public boolean isTokenValid(String token, CustomUserDetails CustomUserDetails) {
    final String username = extractUsername(token);
    return (username.equals(CustomUserDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
               .setSigningKey(getSignInKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
