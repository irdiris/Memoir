package com.example.implmentation.Controllers;

import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserService;

import com.example.implmentation.Security.CustomUserDetailsManager;
import com.example.implmentation.Security.LoginSuccessHandler;
import com.example.implmentation.Security.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("Implementation/user")
public class UserController {


private final PasswordEncoder passwordEncoder;
private final LoginSuccessHandler loginSuccessHandler;
private final TokenGenerator tokenGenerator;
private final CustomUserDetailsManager customUserDetailsManager;

private final UserService userService;



@Autowired
    public UserController(PasswordEncoder passwordEncoder, LoginSuccessHandler loginSuccessHandler, TokenGenerator tokenGenerator, CustomUserDetailsManager customUserDetailsManager, UserService userService) {


    this.passwordEncoder = passwordEncoder;
    this.loginSuccessHandler = loginSuccessHandler;
    this.tokenGenerator = tokenGenerator;
    this.customUserDetailsManager = customUserDetailsManager;
    this.userService = userService;
}
    @PostMapping("/Register")
    public ResponseEntity<String> Register(@ModelAttribute User user) throws IOException {
     userService.register(user);
      return  new ResponseEntity<>("User saved." ,HttpStatus.OK);

    }

    @PostMapping("/Authenticate")
    public void Authenticate(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Authentication  authentication= userService.authenticate(user, request, response);
        String token= tokenGenerator.generateToken(customUserDetailsManager.loadUserByUsername(authentication.getName()));
        response.setHeader("Authorization", "Bearer " +token);
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

    }

}
