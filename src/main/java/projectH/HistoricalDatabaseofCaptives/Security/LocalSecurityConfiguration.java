package projectH.HistoricalDatabaseofCaptives.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity(debug = true)
@Configuration
public class LocalSecurityConfiguration {

    @Autowired
    private AuthenticationEndpointComponent authenticationEntryPoint ;




    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication().withUser("hdc_user1").password("123")
                .authorities("NO");
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSec) throws Exception {
        httpSec.authorizeRequests()
                .antMatchers("/auth/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

//        httpSec.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
        return httpSec.build();
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
