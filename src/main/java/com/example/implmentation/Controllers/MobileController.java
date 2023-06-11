package com.example.implmentation.Controllers;

import com.example.implmentation.Models.InventoryCheck.InventoryCheck;
import com.example.implmentation.Models.InventoryCheck.InventoryCheckRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.MobileLogin;
import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Models.User.UserService;
import com.example.implmentation.Security.CustomUserDetailsManager;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller

@RequestMapping("/Mobile")
public class MobileController {
    private final UserRepository userRepository;
    private final CustomUserDetailsManager customUserDetailsManager;

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final InventoryCheckRepository inventoryCheckRepository;
    private final ItemsRepository itemsRepository;

@Autowired
    public MobileController(UserRepository userRepository, CustomUserDetailsManager customUserDetailsManager, UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, InventoryCheckRepository inventoryCheckRepository, ItemsRepository itemsRepository) {
        this.userRepository = userRepository;
    this.customUserDetailsManager = customUserDetailsManager;
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.inventoryCheckRepository = inventoryCheckRepository;
    this.itemsRepository = itemsRepository;
}

    @PostMapping("Login")
        public ResponseEntity<String> Login(@RequestBody MobileLogin mobileLogin, HttpServletResponse response, HttpServletRequest request) throws IOException {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userRepository.findById(mobileLogin.getId()).get().getUsername(), mobileLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
   return new ResponseEntity<>("true", HttpStatus.OK);

    }
    @PostMapping("checkCode")
    public ResponseEntity<String> checkCode(@RequestBody String body, HttpServletResponse response, HttpServletRequest request) throws IOException {

        if (inventoryCheckRepository.existsByItem(Items.builder()
                        .serialNumber(body.substring(3))
                .build())){

            inventoryCheckRepository.deleteByItem(Items.builder()
                    .serialNumber(body.substring(3))
                    .build());
            return new ResponseEntity<>("Done", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("item not found", HttpStatus.OK);
        }


    }
    @GetMapping("/checklist")
    @ResponseBody
    public ResponseEntity<List<InventoryCheck>>getList() {
   List<InventoryCheck> inventoryCheck = inventoryCheckRepository.findAll();
      return new ResponseEntity<List<InventoryCheck>>(inventoryCheck,HttpStatus.OK);

    }
}


