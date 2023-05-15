package com.example.implmentation.Controllers;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.Student.StudentService;
import com.example.implmentation.Models.User.User;
import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Security.CustomUserDetailsManager;
import com.example.implmentation.Security.TokenGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/View")
public class StudentViewHandler {
public final StudentService studentService;
private  final UserRepository userRepository;
private  final AuthenticationManager authenticationManager;
private  final CustomUserDetailsManager customUserDetailsManager;
private final TokenGenerator tokenGenerator;
@Autowired
    public StudentViewHandler(StudentService studentService, UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator) {
        this.studentService = studentService;
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.customUserDetailsManager = customUserDetailsManager;
    this.tokenGenerator = tokenGenerator;
}

    @RequestMapping ("/Student")
    public ModelAndView studentLander(User user, HttpServletRequest request, HttpServletResponse response  ) {
System.out.println("Logged in");
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken( user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails= customUserDetailsManager.loadUserByUsername(user.getUsername());

        String token  = tokenGenerator.generateToken(userDetails);
        response.setHeader("Authorization", "Bearer " +token);
        List<Notifications> notifications= studentService.getNotifications(userRepository.getUserByUsername(user.getUsername()).get().getId());

        List<EquipmentRequests> equipmentRequestsList = studentService.getHistory(userRepository.getUserByUsername(user.getUsername()).get().getId());
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("token",  "Bearer " +token);
        modelAndView.addObject("user", userRepository.getUserByUsername(user.getUsername()).get().getUsername());
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Student/Student-AllocatedEquipmentHistory");
        return modelAndView ;
    }
    @GetMapping("/Student/Request")
    public ModelAndView studentRequest(HttpServletRequest request){
    List<Items> allocationItems = studentService.getItemsForAllocation();
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("items", allocationItems);
        modelAndView.setViewName("/Student/Student-RequestEquipment");
    return  modelAndView;
    }
    @GetMapping("/Student/History")
    public ModelAndView studentHistory(HttpServletRequest request){

        List<EquipmentRequests> equipmentRequestsList = studentService.getHistory( userRepository.getUserByUsername( tokenGenerator.extractUsername(  request.getHeader("Authorization").substring(7))).get().getId() );
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.setViewName("/Student/Student-AllocatedEquipmentHistory");
        return modelAndView ;
    }

    @GetMapping("Student/Update")
    public ModelAndView UpdatePage(){
    ModelAndView modelAndView = new ModelAndView("/Student/Student-ManageMyAccount");
    return modelAndView;
    }
}