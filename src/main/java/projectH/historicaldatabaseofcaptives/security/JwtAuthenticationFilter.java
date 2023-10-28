package projectH.historicaldatabaseofcaptives.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projectH.historicaldatabaseofcaptives.users.User;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
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
    if (userName != null && SecurityContextHolder.getContext().getAuthentication() != null){
        User userDetails = (User) this.userDetailsService.loadUserByUsername(userName);
        userDetails.getAge();
    }

    }
}
