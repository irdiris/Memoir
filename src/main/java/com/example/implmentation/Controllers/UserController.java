package com.example.implmentation.Controllers;

import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Models.User.UserService;
import com.example.implmentation.Security.AuthenticationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("Implementation/user")
public class UserController {


private final PasswordEncoder passwordEncoder;

private final UserService userService;
private final UserRepository userRepository;

@Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
}
    @PostMapping("/Register")
    public ResponseEntity<String> Register(@RequestBody User user) throws IOException {
     userService.register(user);


      return  new ResponseEntity<>("User saved." ,HttpStatus.OK);

    }

    @PostMapping("/Authenticate")
    public ResponseEntity<AuthenticationResponse> Authenticate(@RequestParam String username, @RequestParam  String password){
          String token = userService.authenticate(User.builder()
                          .username(username)
                          .password(password)
                  .build());

        return  new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
    }

}
