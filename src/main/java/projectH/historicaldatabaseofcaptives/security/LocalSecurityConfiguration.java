package projectH.historicaldatabaseofcaptives.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectH.historicaldatabaseofcaptives.applicationexceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import projectH.historicaldatabaseofcaptives.users.UserRepository;


@EnableWebSecurity(debug = true)
@Configuration
public class LocalSecurityConfiguration {

    private final UserRepository userRepository;
    private final LocalSecurityConfiguration  localSecurityConfiguration;

// everything will be jwt


    public LocalSecurityConfiguration(UserRepository userRepository, LocalSecurityConfiguration localSecurityConfiguration) {
        this.userRepository = userRepository;
        this.localSecurityConfiguration = localSecurityConfiguration;
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username)
                .orElseThrow( () -> new UserNotFoundException("user with the following username does not exists" + username))
                ;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(localSecurityConfiguration.userDetailsService());
        authProvider.setPasswordEncoder(localSecurityConfiguration.passwordEncoder());
        return authProvider;
    }

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }






}
