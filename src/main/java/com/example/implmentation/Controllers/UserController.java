package com.example.implmentation.Controllers;

import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserRepository;
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
private final UserRepository userRepository;



@Autowired
    public UserController(PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator, CustomUserDetailsManager customUserDetailsManager, UserService userService, UserRepository userRepository) {


    this.passwordEncoder = passwordEncoder;
    this.tokenGenerator = tokenGenerator;
    this.customUserDetailsManager = customUserDetailsManager;
    this.userService = userService;
    this.userRepository = userRepository;
}
    @PostMapping("/Register")
    public ModelAndView Register(@ModelAttribute User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
     userService.register(user);
     return  Authenticate(user,response,request);

    }

    @PostMapping("/Authenticate")
    public ModelAndView Authenticate(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws IOException {

        UserDetails userDetails = userService.authenticate(user, request,response);
        String role = userDetails.getAuthorities().toString();

        if (userRepository.findByUsername(user.getUsername()).get().getState().contains("Active")) {
            if (role.contains("Researcher")) {

                return new ModelAndView("forward:/Researcher/Lander");
            }
            if (role.contains("Student")) {

                return new ModelAndView("forward:/View/Student");
            }
            if (role.contains("Allocation Manager")) {

                return new ModelAndView("forward:/AllocationManager/AllocationManagerLander");
            }
            if (role.contains("Inventory Manager")) {

                return new ModelAndView("forward:/InventoryManager/InventoryManagerLander");
            }
            if (role.contains("Admin")) {

                return new ModelAndView("forward:/Admin/Lander");
            }
        } else {
            return new ModelAndView("forward:/Admin/Lander");

    }return null;}
    @RequestMapping("/Error")
    public ModelAndView error(){
     ModelAndView modelAndView= new ModelAndView("forward:/Implementation/user/Error");
        return modelAndView;
    }}
