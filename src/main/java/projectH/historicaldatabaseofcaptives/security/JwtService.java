package projectH.historicaldatabaseofcaptives.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtService {


    public String getUserName(String jwToken) {
        return null;
        //need the io.jasonwebtoken maven dep
    }

//    private Claims getClaims(String token) {
////        return Jwts.parser().setSigningKey(getSignInKey()).build().parseSignedClaims(token);
//    }
}