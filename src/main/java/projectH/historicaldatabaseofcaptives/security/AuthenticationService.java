package projectH.historicaldatabaseofcaptives.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import projectH.historicaldatabaseofcaptives.users.User;
import projectH.historicaldatabaseofcaptives.users.UserRepository;

@Service
public class AuthenticationService {

    // no sign up option yet first registration will be manual through the db
    // change password option should be implemented.


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
       authenticationManager.authenticate
               (
               new UsernamePasswordAuthenticationToken(
                       authenticationRequest.getUsername(),
                       authenticationRequest.getPassword()
               )
       );
       User user = (User) userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.createToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}
