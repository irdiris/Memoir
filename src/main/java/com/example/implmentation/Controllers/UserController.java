package com.example.implmentation.Controllers;

import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Security.CustomUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Implementation/user")
public class UserController {
    private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

@Autowired
    public UserController(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

    }
    @PostMapping("/Register")
    public ResponseEntity<String> Register(@RequestBody User user){
      if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsById(user.getId())){
          return new ResponseEntity< >("User already exists.", HttpStatus.BAD_REQUEST);
      }
      User goodUser= User.builder()
              .email(user.getEmail())
              .id(user.getId())
              .username(user.getUsername())
              .password(passwordEncoder.encode(user.getPassword()))
              .type(user.getType())
              .build();
      userRepository.save(goodUser);
      return  new ResponseEntity<>("User saved." ,HttpStatus.OK);

    }
}
