package com.example.implmentation.Security;

import jakarta.websocket.OnClose;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

    httpSecurity
            .csrf()
            .disable()
            .authorizeRequests()
           .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("Implementation/user/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/LoginPage")
            .failureUrl("/Login?error=true")
            .permitAll();


    return httpSecurity.build();

}

@Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration) throws  Exception{
    return  authenticationConfiguration.getAuthenticationManager();
}
@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}





    }



