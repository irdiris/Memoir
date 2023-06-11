package com.example.implmentation.Controllers;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
import com.example.implmentation.Models.HPCRequests.HPCRequests;
import com.example.implmentation.Models.HPCRequests.HPCRequestsRepository;
import com.example.implmentation.Models.HPCSchedule.HPCScheduleRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.Notifications.NotificationRepository;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/Researcher")
public class ResearcherViewHandler {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;
    private final NotificationRepository notificationRepository;
    private  final EquipmentRequestsRepository equipmentRequestsRepository;
    private final HPCScheduleRepository hpcScheduleRepository;
    private final ResearcherRepository researcherRepository;
    private final ItemsRepository itemsRepository;
    private final HPCRequestsRepository hpcRequestsRepository;

    @Autowired
    public ResearcherViewHandler(UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator, NotificationRepository notificationRepository, EquipmentRequestsRepository equipmentRequestsRepository, HPCScheduleRepository hpcScheduleRepository, ResearcherRepository researcherRepository, ItemsRepository itemsRepository, HPCRequestsRepository hpcRequestsRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
        this.notificationRepository = notificationRepository;
        this.equipmentRequestsRepository = equipmentRequestsRepository;
        this.hpcScheduleRepository = hpcScheduleRepository;
        this.researcherRepository = researcherRepository;
        this.itemsRepository = itemsRepository;
        this.hpcRequestsRepository = hpcRequestsRepository;
    }
    @RequestMapping("/Lander")
    public ModelAndView Researcherlander(User user, HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userDetails = customUserDetailsManager.loadUserByUsername(user.getUsername());

        String token = tokenGenerator.generateToken(userDetails);
        response.setHeader("Authorization", "Bearer " + token);
        System.out.println(user.getId());
        List<Notifications> notifications = notificationRepository.getNotificationsById(userRepository.getUserByUsername(user.getUsername()).get().getId().intValue());

        List<EquipmentRequests> equipmentRequestsList = equipmentRequestsRepository.getHistory(userRepository.getUserByUsername(user.getUsername()).get().getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("token", "Bearer " + token);
        modelAndView.addObject("user", userRepository.getUserByUsername(user.getUsername()).get().getUsername());
        modelAndView.addObject("history", equipmentRequestsList);
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Researcher/Researcher-AllocatedEquipmentHistory");
        return modelAndView;

    }
    @GetMapping("/HPCHistory")
    public ModelAndView HPCHistory(HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("HPCs", hpcScheduleRepository.findByResearcher(researcherRepository.findById(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId()).get()));
        modelAndView.setViewName("/Researcher/hpctable");
        return modelAndView;
    }
    @GetMapping("/HPCHistoryPage")
    public ModelAndView HPCHistoryPage(HttpServletRequest request){
        List<Notifications> notifications = notificationRepository.getNotificationsById(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId().intValue());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("notification", notifications);
        modelAndView.setViewName("/Researcher/Researcher-AllocatedHPCHistory");
        return modelAndView;
    }

    @GetMapping("/Request")
    public ModelAndView Request(HttpServletRequest request) {
        List<Items> allocationItems = itemsRepository.findItemsForAllocating();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("items", allocationItems);

        modelAndView.setViewName("/Researcher/Researcher-RequestEquipment");
        return modelAndView;
    }
    @GetMapping("/Notification")
    public ModelAndView notification(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        List<Notifications> notifications = notificationRepository.getNotificationsById(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId().intValue());

        modelAndView.addObject("notification", notifications);
return modelAndView;
    }
    @PostMapping("/sendRequest")
    public String sendRequest(HttpServletRequest request){

        EquipmentRequests equipmentRequests =   EquipmentRequests.builder()
                .itemId(itemsRepository.findItems((request.getParameter("serialNumber"))))
                .dateOfReturn(request.getParameter("dateOfReturn"))
                .dateOfAcquisition(request.getParameter("dateOfAcquisition"))
                .userId(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get())
                .state("Pending")
                .build();

        equipmentRequestsRepository.save(equipmentRequests);
        Items item = itemsRepository.findItems(equipmentRequests.getItemId().getSerialNumber());
        item.setState("Pending");
       itemsRepository.save(item);

        System.out.println("saved");
        return ("/Student/Student-RequestEquipment");
    }
    @GetMapping("/RequestHPC")
    public ModelAndView RequestHPC(HttpServletRequest request) {
     List<Items> hpcList = itemsRepository.findHPCForAllocating();
        List<HPCRequests>  hpcRequests =hpcRequestsRepository.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("hpclist", hpcList);
        modelAndView.addObject("request", hpcRequests);
        modelAndView.setViewName("/Researcher/Researcher-RequestHPC");
        return modelAndView;
    }
    @PostMapping("/sendHPCRequest")
    public String sendHPCRequest(HttpServletRequest request){
System.out.println(request.getParameter("serialNumber"));
        HPCRequests hpcRequests =   HPCRequests.builder()
                .item(itemsRepository.findItems((request.getParameter("serialNumber"))))
                .dateOfAcquisition(request.getParameter("dateOfAcquisition"))
                .researcher(researcherRepository.findById(userRepository.getUserByUsername(tokenGenerator.extractUsername(request.getHeader("Authorization").substring(7))).get().getId()).get())
                .build();

        hpcRequestsRepository.save(hpcRequests);
        Items item = itemsRepository.findItems(hpcRequests.getItem().getSerialNumber());
        item.setState("Pending");
        itemsRepository.save(item);

        System.out.println("saved");
        return ("/Student/Student-RequestEquipment");
    }
}
