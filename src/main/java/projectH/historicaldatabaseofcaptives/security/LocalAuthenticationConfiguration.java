package projectH.historicaldatabaseofcaptives.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projectH.historicaldatabaseofcaptives.users.UserPermission;

@Configuration
@EnableWebSecurity(debug = true)
public class LocalAuthenticationConfiguration {
    private  final  JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    private static final String[] PUBLIC_ENDPOINTS = {
            "/allTheCaptives",
            "/LocationsOfResidence",
            "/SexDistributionPerCities",
            "/relocations",
            "/whoWasSimilarToMe",
            "/v1/test"

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

    public LocalAuthenticationConfiguration(JwtAuthenticationFilter jwtAuthFilter,
                                            AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;

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

}
