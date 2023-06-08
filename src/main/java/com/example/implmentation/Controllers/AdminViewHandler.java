package com.example.implmentation.Controllers;

import com.example.implmentation.Models.Categories.Categories;
import com.example.implmentation.Models.Categories.CategoryRepository;
import com.example.implmentation.Models.EquipmentRequests.Request;
import com.example.implmentation.Models.Items.ItemsRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class AdminViewHandler {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;
    private final ItemsRepository itemsRepository;
    private  final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
@Autowired
    public AdminViewHandler(UserRepository userRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator, ItemsRepository itemsRepository, CategoryRepository categoryRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
    this.itemsRepository = itemsRepository;
    this.categoryRepository = categoryRepository;
    this.passwordEncoder = passwordEncoder;
}


    @RequestMapping("/Lander")
    public ModelAndView Adminlander(User user, HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = authenticationManager
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
        modelAndView.setViewName("/Admin/Admin-InventoryState");
        return modelAndView;
    }
    @GetMapping("/Categories")
    public ModelAndView getCategories(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categories", categoryRepository.findAll());
        modelAndView.setViewName("/Admin/Admin-ManageEquipmentCategories");
        return modelAndView;
    }
    @GetMapping("/Managers")
    public ModelAndView getManagers(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userRepository.getManagers());
        modelAndView.setViewName("/Admin/Admin-ManageManagers");
        return modelAndView;
    }
    @GetMapping("/categoryUpdate")
    public ModelAndView getCategoryUpdate(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/Admin/Update-EquipmentCategories");
        return modelAndView;
    }
    @PostMapping("/addUsers")
    public ModelAndView addUsers(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request){
    user.setPassword(passwordEncoder.encode(user.getPassword()));
          userRepository.save(user);
        return getManagers();
    }
    @PostMapping("/deleteUsers")
    public ModelAndView deleteUsers(@RequestBody Request requestE, HttpServletResponse response, HttpServletRequest request){
      userRepository.deleteById((long) requestE.getUserId());
        return getManagers();
    }
    @PostMapping("/addCategory")
    public ModelAndView addCategory(@ModelAttribute Categories categories, HttpServletResponse response, HttpServletRequest request){
    categoryRepository.save(categories);
        return getCategories();}

    @PostMapping("/updateCategory")
    public ModelAndView updateCategory( HttpServletResponse response, HttpServletRequest request){
        Optional<Categories> categories= categoryRepository.findById(Long.valueOf(request.getParameter("oldId")));
        categories.get().setName(request.getParameter("name"));
        categories.get().setDescription(request.getParameter("description"));
        categoryRepository.save(categories.get());
        return getCategories();
}
    @PostMapping("/deleteCategory")
    public ModelAndView deleteCategory( HttpServletResponse response, HttpServletRequest request){
        categoryRepository.deleteById(Long.valueOf(request.getParameter("Id")));
        return getManagers();
    }
}
