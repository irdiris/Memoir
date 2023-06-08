package com.example.implmentation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
    public SecurityConfig(CustomFilter customFilter) {
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
                .authorizeHttpRequests()
                .requestMatchers("/templates/Student/**").permitAll()
                .requestMatchers("/templates/Admin/**").permitAll()
                .requestMatchers("/templates/Researcher/**").permitAll()
                .requestMatchers("/templates/AllocationManager/**").permitAll()
                .requestMatchers("/templates/InventoryManager/**").permitAll()
                .requestMatchers("/View/**").permitAll()
                .requestMatchers("/Admin/**").permitAll()
                .requestMatchers("/Researcher/**").permitAll()
                .requestMatchers("/AllocationManager/**").permitAll()
                .requestMatchers("/InventoryManager/**").permitAll()
                .requestMatchers("/Implementation/user/**").permitAll()
                .requestMatchers("/Mobile/**").permitAll()
                .requestMatchers("/Pages/**").permitAll()
                .requestMatchers("/static/css/**", "static/js/**", "static/css/images/**","/resources/**").permitAll()
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
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/static/**");
    }
    }




