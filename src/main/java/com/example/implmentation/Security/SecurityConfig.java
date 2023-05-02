package com.example.implmentation.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

private  final CustomFilter customFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

    httpSecurity
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
           .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("Implementation/user/**").permitAll()
            .requestMatchers("/LoginPage").permitAll()
            .requestMatchers("resources/**").permitAll()
            .requestMatchers("static/css/**", "static/js/**", "static/css/images/**").permitAll()
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



