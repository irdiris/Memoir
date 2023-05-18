package com.example.implmentation.Controllers;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.Student.Student;
import com.example.implmentation.Models.Student.StudentService;
import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Security.CustomUserDetailsManager;
import com.example.implmentation.Security.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/View")
public class StudentViewHandler {
    public final StudentService studentService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;

    @Autowired
    public StudentViewHandler(StudentService studentService, UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator) {
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
    }

    @RequestMapping("/Student")
    public ModelAndView studentLander(User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Logged in");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = customUserDetailsManager.loadUserByUsername(user.getUsername());

        String token = tokenGenerator.generateToken(userDetails);
        response.setHeader("Authorization", "Bearer " + token);
        List<Notifications> notifications = studentService.getNotifications(userRepository.getUserByUsername(user.getUsername()).get().getId());

        List<EquipmentRequests> equipmentRequestsList = studentService.getHistory(userRepository.getUserByUsername(user.getUsername()).get().getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("token", "Bearer " + token);
        modelAndView.addObject("user", userRepository.getUserByUsername(user.getUsername()).get().getUsername());
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Student/Student-AllocatedEquipmentHistory");
        return modelAndView;
    }

    @GetMapping("/Student/Request")
    public ModelAndView studentRequest(HttpServletRequest request) {
        List<Items> allocationItems = studentService.getItemsForAllocation();
        List<Notifications> notifications = studentService.getNotifications(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", allocationItems);
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Student/Student-RequestEquipment");
        return modelAndView;
    }

    @GetMapping("/Student/History")
    public ModelAndView studentHistory(HttpServletRequest request) {
        List<Notifications> notifications = studentService.getNotifications(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId());
        List<EquipmentRequests> equipmentRequestsList = studentService.getHistory(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Student/Student-AllocatedEquipmentHistory");
        return modelAndView;
    }

    @GetMapping("/Student/Update")
    public ModelAndView UpdatePage() {
        return new ModelAndView("/Student/Student-ManageMyAccount");
    }

    @RequestMapping ("/Student/updateProfile")
    public void updateProfile(  HttpServletRequest request){

            studentService.UpdateProfile( Student.builder()
                    .specialty(request.getParameter("specialty"))
                    .level(request.getParameter("level"))
                    .institution(request.getParameter("institution"))
                    .user(User.builder()
                            .firstName(request.getParameter("firstName"))
                            .lastName(request.getParameter("lastName"))
                            .phone(Integer.parseInt(request.getParameter("phone")))
                            .email(request.getParameter("email"))
                            .username(request.getParameter("username"))
                            .password(request.getParameter("password"))
                            .birthDate(request.getParameter("birthDate"))
                            .Address(request.getParameter("address"))
                            .build())
                    .build(), tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7)));

    }
}
