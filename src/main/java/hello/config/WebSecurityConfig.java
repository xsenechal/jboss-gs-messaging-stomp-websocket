package hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        users.put("u","u,ROLE_USER,enabled"); //add whatever other user you need
        users.put("a","a,ROLE_USER,enabled"); //add whatever other user you need
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }





}