package projectH.HistoricalDatabaseofCaptives.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,
                                     @NonNull HttpServletResponse response,
                                     @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authenticationHeader = request.getHeader("Authorisation");
    final String jwToken;
    final String userName;


    if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")){
        filterChain.doFilter(request, response);
        return;
    }
    jwToken = authenticationHeader.substring(7);
    userName = jwtService.getUserName(jwToken);


    }
}
