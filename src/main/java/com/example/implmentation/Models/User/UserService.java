package com.example.implmentation.Models.User;

import com.example.implmentation.Security.CustomUserDetailsManager;
import com.example.implmentation.Security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;
    private final  ExcelService excelService;
    private final UserRepository userRepository;
    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator, ExcelService excelService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
        this.excelService = excelService;
        this.userRepository = userRepository;
    }

    public String authenticate( User user){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails= customUserDetailsManager.loadUserByUsername(user.getUsername());
        String token  = tokenGenerator.generateToken(userDetails);
        return token;
    }
    public void register(User expectedUser) throws IOException {
        if (userRepository.existsById(expectedUser.getId())) {
          System.out.println("User already exists.");
        }
        else {


              User user = User.builder()
                .id(expectedUser.getId())
                .username(expectedUser.getUsername())
                .email(expectedUser.getEmail())
                .password(passwordEncoder.encode(expectedUser.getPassword()))
                .type(expectedUser.getType())
                .build();
           excelService.registerUser(user);

        }
    }
}
