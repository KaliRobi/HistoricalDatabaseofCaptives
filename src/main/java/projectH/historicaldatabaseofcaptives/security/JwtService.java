package projectH.historicaldatabaseofcaptives.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtService {

    private static final String SECRET = "D363591A0BDBC0E57C0C535C7EE256396A1B9280A03465812FBFE430F4C02B89";

     public String getUserName(String token) {
        return getClaim(token, Claims::getIssuer);

    }
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }
    public String createTokenFromUserDetails(UserDetails userDetails){
    return createToken(new HashMap<>(), userDetails);
    }

    public  String createToken(Map<String, Objects> claimsToAdd, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claimsToAdd)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*24*60))
                .signWith(getSignInKey())
                .compact();
     }
    public  String createToken( UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*24*60))
                .signWith(getSignInKey())
                .compact();
    }

     final boolean isTokenValid(String token, UserDetails userDetails){
         final String userName = getUserName(token);
         return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
     }

    private boolean isTokenExpired(String token) {
         return getTokenExpiryDate(token).before(new Date()) ;
    }

    private Date getTokenExpiryDate(String token) {
         return getClaim(token, Claims::getExpiration);
    }


}