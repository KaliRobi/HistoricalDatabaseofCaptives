package projectH.historicaldatabaseofcaptives.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
public class LocalAuthenticationProvider {

  private final LocalSecurityConfiguration localSecurityConfiguration;

    public LocalAuthenticationProvider(LocalSecurityConfiguration localSecurityConfiguration) {
        this.localSecurityConfiguration = localSecurityConfiguration;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(localSecurityConfiguration.userDetailsService());
        authProvider.setPasswordEncoder(localSecurityConfiguration.passwordEncoder());
        return authProvider;
    }


}
