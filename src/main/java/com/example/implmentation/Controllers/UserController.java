package com.example.implmentation.Controllers;

import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserService;

import com.example.implmentation.Security.CustomUserDetailsManager;
import com.example.implmentation.Security.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("Implementation/user")
public class UserController {


private final PasswordEncoder passwordEncoder;

private final TokenGenerator tokenGenerator;
private final CustomUserDetailsManager customUserDetailsManager;

private final UserService userService;



@Autowired
    public UserController(PasswordEncoder passwordEncoder,  TokenGenerator tokenGenerator, CustomUserDetailsManager customUserDetailsManager, UserService userService) {


    this.passwordEncoder = passwordEncoder;
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
    public ModelAndView Authenticate(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws IOException {

        UserDetails userDetails = userService.authenticate(user, request,response);
        if (userDetails.getAuthorities().toString().contains("Student")){
            return  new ModelAndView("forward:/View/Student");
        }


        return null;
    }}
