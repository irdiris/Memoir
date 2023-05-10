package com.example.implmentation.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Pages")
public class PageController {
    @GetMapping("/LanderPage")
    public String Landing(){
        return ("Landing page");
    }
    @GetMapping("/LoginPage")
    public String Login(){
        return ("Login & Sign-up");
    }


}
