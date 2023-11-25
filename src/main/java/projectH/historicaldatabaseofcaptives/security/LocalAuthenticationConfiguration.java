package projectH.historicaldatabaseofcaptives.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity(debug = true)
public class LocalAuthenticationConfiguration {
    private  final  JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/v1/allTheCaptives",
            "/v1/LocationsOfResidence",
            "/v1/SexDistributionPerCities",
            "/v1/relocations",
            "/v1/whoWasSimilarToMe",
            "/v1/authenticate"
    };

    private static final String[] USER_ENDPOINTS = {
            "/v1/postNewCaptive",
            "/v1/AddAbbrevs",
            "/v1/updateCaptiveV2/{id}"
    };

    private static final String[] ADMIN_ENDPOINTS = {
//  not sure that admin console will be done before the first release
    };

    public LocalAuthenticationConfiguration(JwtAuthenticationFilter jwtAuthFilter,
                                            AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {
        httpSec.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->  {
                    authorizationManagerRequestMatcherRegistry.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
//                    authorizationManagerRequestMatcherRegistry
//                            .requestMatchers("/v1/relocations").hasRole("ADMIN");
//                    authorizationManagerRequestMatcherRegistry
//                            .requestMatchers("/v1/authenticate").hasRole("ADMIN");
                })
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSec.build();
    }

}
