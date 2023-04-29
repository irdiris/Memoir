package com.example.implmentation.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {
    @GetMapping("/LoginPage")
    public String login(){
        return ("Login & Sign-up");
    }

}
