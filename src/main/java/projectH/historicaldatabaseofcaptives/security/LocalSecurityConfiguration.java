package projectH.historicaldatabaseofcaptives.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projectH.historicaldatabaseofcaptives.applicationexceptions.UserNotFoundException;
import projectH.historicaldatabaseofcaptives.users.UserPermission;
import org.springframework.security.core.userdetails.UserDetailsService;
import projectH.historicaldatabaseofcaptives.users.UserRepository;


@EnableWebSecurity(debug = true)
@Configuration
public class LocalSecurityConfiguration {


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserRepository userRepository;

// everything will be jwt
    private static final String[] PUBLIC_ENDPOINTS = {
            "/allTheCaptives",
            "/LocationsOfResidence",
            "/SexDistributionPerCities",
            "/relocations",
            "/whoWasSimilarToMe"

    };

    private static final String[] USER_ENDPOINTS = {
            "/postNewCaptive",
            "/AddAbbrevs",
            "/updateCaptiveV2/{id}",
            "/relocations"

    };

    private static final String[] ADMIN_ENDPOINTS = {
            //not sure that admin console will be done before the first release

    };

    public LocalSecurityConfiguration( AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter, UserRepository userRepository) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {
        httpSec.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                    authorizationManagerRequestMatcherRegistry.requestMatchers(USER_ENDPOINTS).hasAuthority(UserPermission.USER.name());
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSec.build();
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


    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }




}
