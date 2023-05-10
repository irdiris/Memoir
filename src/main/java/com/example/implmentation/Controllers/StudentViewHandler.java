package com.example.implmentation.Controllers;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.Student.StudentService;
import com.example.implmentation.Models.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/View")
public class StudentViewHandler {
public final StudentService studentService;
private  final UserRepository userRepository;
@Autowired
    public StudentViewHandler(StudentService studentService, UserRepository userRepository) {
        this.studentService = studentService;
    this.userRepository = userRepository;
}

    @GetMapping("/Student")
    public ModelAndView student(@RequestParam String username ) {

        List<EquipmentRequests> equipmentRequestsList = studentService.getHistory(userRepository.getUserByUsername(username).get().getId());
        ModelAndView modelAndView= new ModelAndView();
        for (EquipmentRequests equipmentRequests: equipmentRequestsList) {
            System.out.println("Name: " + equipmentRequests.getItemId().getSerialNumber());
        }
        modelAndView.addObject("user", userRepository.getUserByUsername(username));
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.setViewName("/Student/Student-AllocatedEquipmentHistory");
        return modelAndView ;
    }
}