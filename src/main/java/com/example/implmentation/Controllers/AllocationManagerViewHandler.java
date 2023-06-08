package com.example.implmentation.Controllers;

import com.example.implmentation.Models.AllocationManager.AllocationManagerService;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.Request;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
import com.example.implmentation.Models.Student.Student;
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

@Controller
@RequestMapping("/AllocationManager")
public class AllocationManagerViewHandler {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;
    private final ItemsRepository itemsRepository;
    private final AllocationManagerService allocationManagerService;
   private final ResearcherRepository researcherRepository;

@Autowired
    public AllocationManagerViewHandler(UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator, ItemsRepository itemsRepository, AllocationManagerService allocationManagerService, ResearcherRepository researcherRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
        this.itemsRepository = itemsRepository;
        this.allocationManagerService = allocationManagerService;
    this.researcherRepository = researcherRepository;
}

    @RequestMapping("/AllocationManagerLander")
    public ModelAndView AllocationManagerLander(User user, HttpServletRequest request, HttpServletResponse response) { Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = customUserDetailsManager.loadUserByUsername(user.getUsername());
        int available = itemsRepository.getItemCount("Available");
        int pending = itemsRepository.getItemCount("Pending");
        int missing = itemsRepository.getItemCount("Missing");
        int allocated = itemsRepository.getItemCount("Allocated");
        int underMaintenance = itemsRepository.getItemCount("Under Maintenance");
        int computers= itemsRepository.getTypeCount("Computers");
        int hpc= itemsRepository.getTypeCount("HPC");
        int robotics= itemsRepository.getTypeCount("Robotics");
        int network= itemsRepository.getTypeCount("Network");
        int security= itemsRepository.getTypeCount("Security");
        int studentCount = userRepository.getUsers("Student");
        int researcherCount = userRepository.getUsers("Researcher");
        int itemsCount = itemsRepository.getItems();
        String token = tokenGenerator.generateToken(userDetails);
        response.setHeader("Authorization", "Bearer " + token);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("studentCount", studentCount);
        modelAndView.addObject("researcherCount", researcherCount);
        modelAndView.addObject("itemsCount", itemsCount);
        modelAndView.addObject("available", available);
        modelAndView.addObject("pending", pending);
        modelAndView.addObject("missing",missing);
        modelAndView.addObject("allocated", allocated);
        modelAndView.addObject("computers",computers);
        modelAndView.addObject("hpc",hpc);
        modelAndView.addObject("robotics",robotics);
        modelAndView.addObject("network",network);
        modelAndView.addObject("security",security);
        modelAndView.addObject("underMaintenance", underMaintenance);
        modelAndView.setViewName("AllocationManager/AllocationManager-InventoryState");
        return modelAndView;
    }
@GetMapping("/AllocationManager/UpdatePage")
    public ModelAndView updatePage(){
        return new ModelAndView("/AllocationManager/AllocationManager-ManageMyAccount");

}
    @GetMapping("/AllocationManager/Researcher")
    public ModelAndView Researcher(){

         ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-manageResearchers");
         modelAndView.addObject("researcher", allocationManagerService.returnAllResearchers());
        return modelAndView;

    }
    @PostMapping("/allocationManager/BanResearcher")
    public String banResearcher( @RequestBody User user, HttpServletRequest request){

      if(user.getState().contains("Active")){
          allocationManagerService.banResearcher(user.getId());
          System.out.println("researcher banned");
      }else {
          allocationManagerService.unbanResearcher(user.getId());
          System.out.println("researcher banned");
      }
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @PostMapping("/AllocationManager/DeleteResearcher")
    public String deleteResearcher( @RequestBody User user, HttpServletRequest request){
    allocationManagerService.deleteResearcher(Math.toIntExact(user.getId()));
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }

    @GetMapping("/AllocationManager/Student")
    public ModelAndView Student(){


        ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-manageStudent");
        modelAndView.addObject("student", allocationManagerService.returnAllStudents());
        return modelAndView;

    }
    @PostMapping("/allocationManager/BanStudent")
    public String banStudent( @RequestBody User user, HttpServletRequest request){

        if(user.getState().contains("Active")){
            allocationManagerService.banStudent(Math.toIntExact(user.getId()));
            System.out.println("researcher banned");
        }else {
            allocationManagerService.unbanStudent(Math.toIntExact(user.getId()));
            System.out.println("researcher banned");
        }
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @PostMapping("/AllocationManager/DeleteStudent")
    public String deleteStudent( @RequestBody User user, HttpServletRequest request){
        allocationManagerService.deleteStudent(Math.toIntExact(user.getId()));
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @GetMapping("/AllocationManager/EquipmentRequests")
    public ModelAndView EquipmentRequests(){


        ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-manageEquipmentRequests");
        modelAndView.addObject("equipment", allocationManagerService.returnAllEquipmentRequests());
        return modelAndView;

    }
    @PostMapping("/AllocationManager/AcceptRequest")
    public String AcceptRequest( @RequestBody Request requestE, HttpServletRequest request){

System.out.println(requestE.getUserId());
System.out.println(requestE.getItemId());
    allocationManagerService.acceptEquipmentRequest(requestE.getItemId(), (long) requestE.getUserId());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }

    @PostMapping("/AllocationManager/RefuseRequest")
    public String RefuseRequest( @RequestBody Request requestE, HttpServletRequest request){

        System.out.println(requestE.getUserId());
        System.out.println(requestE.getItemId());
        allocationManagerService.refuseEquipmentRequest(requestE.getItemId(), (long) requestE.getUserId());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @GetMapping("/AllocationManager/HPCRequests")
    public ModelAndView HpcRequests(){
        ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-manageHPCRequests");
        modelAndView.addObject("hcpRequests", allocationManagerService.returnHPCRequests());
        return modelAndView;

    }
    @PostMapping("/AllocationManager/AcceptHPCRequest")
    public String AcceptHPCRequest( @RequestBody Request requestE, HttpServletRequest request){

        allocationManagerService.acceptHPCRequest((long) requestE.getUserId(), requestE.getItemId(), requestE.getDateOfAcquisition());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @PostMapping("/AllocationManager/RefuseHPCRequest")
    public String RefuseHPCRequest( @RequestBody Request requestE, HttpServletRequest request){

        System.out.println(requestE.getUserId());
        System.out.println(requestE.getItemId());
        allocationManagerService.refuseHPCRequest((long) requestE.getUserId(), requestE.getItemId());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }

    @GetMapping("/AllocationManager/HPCSchedule")
    public ModelAndView HpcSchedule(){

        ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-ReviewHPC");
        modelAndView.addObject("hcpSchedule", allocationManagerService.returnHPCSchedule());
        return modelAndView;

    }
    @GetMapping("/AllocationManager/Equipment")
    public ModelAndView equipment(){

        ModelAndView modelAndView = new ModelAndView("/AllocationManager/AllocationManager-ReviewEquipment");
        modelAndView.addObject("equipment", allocationManagerService.returnEquipment());
        return modelAndView;

    }
    @PostMapping("/AllocationManager/MarkHPCRequest")
    public String MarkHPCRequest( @RequestBody Request requestE, HttpServletRequest request){

        System.out.println(requestE.getUserId());
        System.out.println(requestE.getItemId());
     allocationManagerService.MarkHPCSchedule((long) requestE.getUserId(), requestE.getItemId());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
    @PostMapping("/AllocationManager/MarkEquipment")
    public String MarkEquipment( @RequestBody Request requestE, HttpServletRequest request){

        System.out.println(requestE.getUserId());
        System.out.println(requestE.getItemId());
        allocationManagerService.MarkEquipment((long) requestE.getUserId(), requestE.getItemId());
        return "/AllocationManager/AllocationManager-ManageMyAccount";
    }
}
