package com.example.implmentation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
public class SecurityConfig  {
private  final CustomFilter customFilter;

@Autowired
    public SecurityConfig(CustomFilter customFilter, LoginSuccessHandler loginSuccessHandler) {
        this.customFilter = customFilter;
    }


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
            .requestMatchers("/templates/Student/**").permitAll()
            .requestMatchers("/View/**").permitAll()
            .requestMatchers("Implementation/user/**").permitAll()
            .requestMatchers("/Pages/**").permitAll()
            .requestMatchers("/resources/**").permitAll()
            .requestMatchers("/static/css/**", "static/js/**", "static/css/images/**").permitAll()
            .anyRequest().authenticated()
        .and()
                .formLogin()
                .loginPage("/Pages/LanderPage")

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
@Bean
    public LoginSuccessHandler customLogin(){
    return new LoginSuccessHandler();
}
    }



