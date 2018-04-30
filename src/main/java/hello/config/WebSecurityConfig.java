package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf()
                    // ignore our stomp endpoints since they are protected using Stomp headers
                    .ignoringAntMatchers("/**")
                    .and()
                .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()
                .authorizeRequests()
                    .antMatchers( "/print/*").authenticated()
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll()
                ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("u").password("u").roles("USER")
                        .username("a").password("a").roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}