package com.example.implmentation.Controllers;

import com.example.implmentation.Models.Categories.CategoryRepository;
import com.example.implmentation.Models.InventoryCheck.InventoryCheck;
import com.example.implmentation.Models.InventoryCheck.InventoryCheckRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.Purchases.Purchases;
import com.example.implmentation.Models.Purchases.PurchasesRepository;
import com.example.implmentation.Models.ResourceHistory.ResourceHistory;
import com.example.implmentation.Models.ResourceHistory.ResourceHistoryRepository;
import com.example.implmentation.Models.Resources.Resources;
import com.example.implmentation.Models.Resources.ResourcesRepository;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/InventoryManager")
public class InventoryManagerViewHandler {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final TokenGenerator tokenGenerator;
    private final ItemsRepository itemsRepository;
  private final PurchasesRepository purchasesRepository;
  private  final ResourcesRepository resourcesRepository;
    private  final ResourceHistoryRepository resourceHistoryRepository;
    private final InventoryCheckRepository inventoryCheckRepository;




    @Autowired
    public InventoryManagerViewHandler(UserRepository userRepository, CategoryRepository categoryRepository, AuthenticationManager authenticationManager, CustomUserDetailsManager customUserDetailsManager, TokenGenerator tokenGenerator, ItemsRepository itemsRepository, PurchasesRepository purchasesRepository, ResourcesRepository resourcesRepository, ResourceHistoryRepository resourceHistoryRepository, InventoryCheckRepository inventoryCheckRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsManager = customUserDetailsManager;
        this.tokenGenerator = tokenGenerator;
        this.itemsRepository = itemsRepository;
        this.purchasesRepository = purchasesRepository;

        this.resourcesRepository = resourcesRepository;
        this.resourceHistoryRepository = resourceHistoryRepository;
        this.inventoryCheckRepository = inventoryCheckRepository;
    }
    @RequestMapping("/InventoryManagerLander")
    public ModelAndView AllocationManagerLander(User user, HttpServletRequest request, HttpServletResponse response) {

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
        modelAndView.setViewName("InventoryManager/InventoryManager-InventoryState");
        return modelAndView;
    }

    @GetMapping("/Equipment")
    public ModelAndView getEquipment(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("receipts", purchasesRepository.findAll() );
        modelAndView.addObject("categories", categoryRepository.findAll() );
        modelAndView.addObject("items", itemsRepository.findAll() );
        modelAndView.setViewName("InventoryManager/InventoryManager-ManageEquipments");
 return modelAndView;
    }
    @PostMapping("/addEquipment")
    public ModelAndView addEquipment(@ModelAttribute Items items, HttpServletRequest request){
      items.setCategories(categoryRepository.findByName(request.getParameter("type")).get());
     itemsRepository.save(items);
     return  getEquipment();
    }
    @PostMapping("/DeleteEquipment")
    public ModelAndView DeleteEquipment( HttpServletRequest request){
           itemsRepository.deleteBySerialNumber(request.getParameter("Id"));
        return  getEquipment();
    }
    @GetMapping("/getUpdate")
    public ModelAndView getUpdate(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("receipts", purchasesRepository.findAll() );
        modelAndView.addObject("categories", categoryRepository.findAll() );
        modelAndView.setViewName("InventoryManager/Update-Equipment");
        return  modelAndView;
    }
    @PostMapping("/updateItem")
    public ModelAndView updateItem( HttpServletResponse response, HttpServletRequest request){
       Items item= itemsRepository.findItems(request.getParameter("oldId"));
    item.setPurchases(purchasesRepository.findById(Long.valueOf(request.getParameter("purchases"))).get());
     item.setName(request.getParameter("name"));
      item.setState(request.getParameter("state"));
      item.setCategories(categoryRepository.findByName(request.getParameter("type")).get());
      item.setService(request.getParameter("service"));
      itemsRepository.save(item);
 return  getEquipment();
    }
    @GetMapping("/Purchases")
    public ModelAndView getPurchases(){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("purchases", purchasesRepository.findAll());
        modelAndView.setViewName("/InventoryManager/InventoryManager-Purchases");
        return modelAndView;
    }
    @PostMapping("/addPurchase")
    public ModelAndView addPurchase(@ModelAttribute Purchases purchases){
        purchasesRepository.save(purchases);
        return getPurchases();
    }

    @GetMapping("/Resources")
    public ModelAndView getResources(){

        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("receipts", purchasesRepository.findAll() );
        modelAndView.addObject("categories", categoryRepository.findAll() );
        modelAndView.addObject("resources", resourcesRepository.findAll());
        modelAndView.setViewName("/InventoryManager/InventoryManager-Resources");
        return modelAndView;
    }
    @PostMapping("/addResource")
    public ModelAndView addResource(@ModelAttribute Resources resources ,HttpServletRequest request){
        resources.setCategories(categoryRepository.findByName(request.getParameter("type")).get());
    resourcesRepository.save(resources);
        return getResources();
    }
    @PostMapping("/DeleteResource")
    public ModelAndView DeleteResource(HttpServletRequest request){
        resourcesRepository.deleteById(Long.valueOf(request.getParameter("Id")));
        return getResources();
    }
    @PostMapping("/UpdateResource")
    public ModelAndView UpdateResource(HttpServletRequest request){
       Optional<Resources> resources= resourcesRepository.findById(Long.valueOf(request.getParameter("oldId")));
        resources.get().setName(request.getParameter("name"));
        resources.get().setQuantity(Integer.parseInt((request.getParameter("quantity"))));
        resources.get().setCategories(categoryRepository.findByName(request.getParameter("type")).get());
        resources.get().setPurchases(purchasesRepository.findById(Long.valueOf(request.getParameter("purchases"))).get());
        resourcesRepository.save(resources.get());
        return getResources();
    }
    @GetMapping("/getResourceUpdate")
    public ModelAndView getResourceUpdate(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("receipts", purchasesRepository.findAll() );
        modelAndView.addObject("categories", categoryRepository.findAll() );
        modelAndView.setViewName("InventoryManager/Update-Resource");
        return  modelAndView;
    }

    @GetMapping("/ResourcesHistory")
    public ModelAndView getResourcesHistory(){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("resources", resourcesRepository.findAll());
        modelAndView.addObject("history", resourceHistoryRepository.findAll());
        modelAndView.setViewName("/InventoryManager/InventoryManager-ResourcesHistory");
        return modelAndView;
    }
    @GetMapping("/InventoryCheck")
    public ModelAndView InventoryCheck(){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.addObject("checks", inventoryCheckRepository.findAll());

        modelAndView.setViewName("/InventoryManager/InventoryManager-Check inventory");
        return modelAndView;
    }
    @PostMapping("/DeleteResourceH")
    public ModelAndView DeleteResourceH(HttpServletRequest request){
       resourceHistoryRepository.deleteById(Long.valueOf(request.getParameter("Id")));
        return getResourcesHistory();
    }
    @PostMapping("/AddResourceH")
    public ModelAndView AddResourceH(@ModelAttribute ResourceHistory resourceHistory, HttpServletRequest request){
        resourceHistory.setResources(resourcesRepository.findById(Long.valueOf(request.getParameter("resource"))).get());
       resourceHistoryRepository.save(resourceHistory);
        return getResourcesHistory();
    }
    @PostMapping("/TriggerCheck")
    public ModelAndView TriggerCheck(@ModelAttribute ResourceHistory resourceHistory, HttpServletRequest request){
        List<Items>  itemslist =itemsRepository.findAll();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);
        for (Items item: itemslist){
            item.setState("Missing");
            System.out.println(item.getName());
            inventoryCheckRepository.save(InventoryCheck.builder()
                            .item(item)
                            .name(item.getName())
                            .status(item.getState())
                            .LastSeen(item.getService())
                            .DateOfCheck(dateString)
                    .build());
        }
        return InventoryCheck();
    }
}
