package com.example.implmentation.Models.AllocationManager;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
import com.example.implmentation.Models.HPCRequests.HPCRequests;
import com.example.implmentation.Models.HPCRequests.HPCRequestsRepository;
import com.example.implmentation.Models.HPCSchedule.HPCSchedule;
import com.example.implmentation.Models.HPCSchedule.HPCScheduleRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.Notifications.NotificationRepository;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
import com.example.implmentation.Models.Student.Student;
import com.example.implmentation.Models.Student.StudentRepository;
import com.example.implmentation.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AllocationManagerService {
    private final StudentRepository studentRepository;
    private final ResearcherRepository researcherRepository;
    private  final EquipmentRequestsRepository equipmentRequestsRepository;
    private final NotificationRepository notificationRepository;
    private final AllocationManagerRepository allocationManagerRepository;
    private final HPCRequestsRepository hpcRequestsRepository;
    private final HPCScheduleRepository hpcScheduleRepository;
    private final UserRepository userRepository;
    private final ItemsRepository itemsRepository;

    @Autowired
    public AllocationManagerService(StudentRepository studentRepository, ResearcherRepository researcherRepository, EquipmentRequestsRepository equipmentRequestsRepository, NotificationRepository notificationRepository, AllocationManagerRepository allocationManagerRepository, HPCRequestsRepository hpcRequestsRepository, HPCScheduleRepository hpcScheduleRepository, UserRepository userRepository, ItemsRepository itemsRepository) {
        this.studentRepository = studentRepository;
        this.researcherRepository = researcherRepository;
        this.equipmentRequestsRepository = equipmentRequestsRepository;
        this.notificationRepository = notificationRepository;
        this.allocationManagerRepository = allocationManagerRepository;
        this.hpcRequestsRepository = hpcRequestsRepository;
        this.hpcScheduleRepository = hpcScheduleRepository;
        this.userRepository = userRepository;
        this.itemsRepository = itemsRepository;
    }

    public List<EquipmentRequests> returnAllEquipmentRequests(){
        return equipmentRequestsRepository.getPending();
    }
    public void refuseEquipmentRequest (String itemId, Long userId){
       equipmentRequestsRepository.deleteRequestByUserIdAndItemId(userId,itemId);
         Items item = itemsRepository.findItems(itemId);
         item.setState("Available");
       itemsRepository.save(item);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);
        Notifications notifications = Notifications.builder()
                .id((int) (userRepository.findById(userId).get().getId()+5))
                .notificationText("Your request for for item "+itemId+" has been denied.")
                .expirationDate(dateString)
                .user(userRepository.findById(userId).get())
                .build();
        notificationRepository.save(notifications);

    }

 public void acceptEquipmentRequest(String itemId, Long userId){
        Optional<EquipmentRequests> equipmentRequestPending= equipmentRequestsRepository.findStateByUserIdAndItemId(userId,itemId);
        equipmentRequestPending.get().setState("Allocated");
        equipmentRequestPending.get().getItemId().setState("Allocated");
        equipmentRequestsRepository.save(equipmentRequestPending.get());
     LocalDate currentDate = LocalDate.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     String dateString = currentDate.format(formatter);
     Notifications notifications = Notifications.builder()
             .id((int) (userRepository.findById(userId).get().getId()+5))
             .expirationDate(dateString)
             .notificationText("Your request for for item "+itemId+" has been approved. Head to the allocation service as soon as possible.")
             .user(userRepository.findById(userId).get())
             .build();
     notificationRepository.save(notifications);
 }
    public void deleteStudent(int id) {
        studentRepository.deleteById((long) id);
    }

    public List<Student> returnAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    public void banStudent(int id) {
        Optional<Student> studentOld = studentRepository.findById((long) id);
        studentOld.get().getUser().setState("Banned");
        studentRepository.save(studentOld.get());

    }

    public void unbanStudent(int id) {
        Optional<Student> studentOld = studentRepository.findById((long) id);
        studentOld.get().getUser().setState("Active");
        studentRepository.save(studentOld.get());

    }



    public void deleteResearcher(int id) {
        researcherRepository.deleteById((long) id);
    }

    public List<Researcher> returnAllResearchers() {
        List<Researcher> researcherList = researcherRepository.findAll();
        return researcherList;
    }

    public void banResearcher(Long id) {
        Optional<Researcher> researcherOld = researcherRepository.findById(id);
        researcherOld.get().getUser().setState("Banned");
        researcherRepository.save(researcherOld.get());
    }
    public void unbanResearcher(Long id) {
        Optional<Researcher> researcherOld = researcherRepository.findById(id);
        researcherOld.get().getUser().setState("Active");
        researcherRepository.save(researcherOld.get());
    }
   public void updateProfile (AllocationManager allocationManager){
        Optional<AllocationManager> allocationManagerOld = allocationManagerRepository.findById(allocationManager.getId());
        allocationManagerOld.get().getUser().setPassword(allocationManager.getUser().getPassword());
       allocationManagerOld.get().getUser().setAddress(allocationManager.getUser().getAddress());
       allocationManagerOld.get().getUser().setEmail(allocationManager.getUser().getEmail());
       allocationManagerOld.get().getUser().setFirstName(allocationManager.getUser().getFirstName());
       allocationManagerOld.get().getUser().setLastName(allocationManager.getUser().getLastName());
       allocationManagerOld.get().getUser().setPhone(allocationManager.getUser().getPhone());
       allocationManagerOld.get().getUser().setUsername(allocationManager.getUser().getUsername());
       allocationManagerOld.get().setStatus(allocationManager.getStatus());
   }

   public List<HPCSchedule> returnHPCSchedule(){
        HPCSchedule hpcSchedule;

       return hpcScheduleRepository.findAll();

   }
    public List<HPCRequests> returnHPCRequests(){
        return hpcRequestsRepository.findAll();
    }
  public void refuseHPCRequest(Long userId, String serialNumber){
        hpcRequestsRepository.deleteRequestByUserIdAndItemId(userId,serialNumber);
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String dateString = currentDate.format(formatter);
       Notifications notifications = Notifications.builder()
               .expirationDate(dateString)
               .notificationText("Your request for for item "+serialNumber+" has been denied.")
               .user(userRepository.findById(userId).get())
               .build();
       notificationRepository.save(notifications);
      Items items =itemsRepository.findItems(serialNumber);
      items.setState("Available");
      itemsRepository.save(items);
   }
   public void acceptHPCRequest(Long userId, String serialNumber, String dateOfAcquisition ){
       hpcRequestsRepository.deleteRequestByUserIdAndItemId(userId, serialNumber);
       LocalDate currentDate = LocalDate.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       String dateString = currentDate.format(formatter);
       HPCSchedule hpcSchedule= HPCSchedule.builder()
               .dateOfAcquisition(dateOfAcquisition)
               .items(itemsRepository.findItems(serialNumber))
               .researcher(researcherRepository.findById(userId).get())
               .state("Allocated")
               .build();
     Items items =itemsRepository.findItems(serialNumber);
      items.setState("Allocated");
      itemsRepository.save(items);
       hpcScheduleRepository.save(hpcSchedule);
       Notifications notifications = Notifications.builder()
               .expirationDate(dateString)
               .notificationText("Your request for item "+serialNumber+" has been accepted. you are free to use it at the assigned hours.")
               .user(userRepository.findById(userId).get())
               .build();
       notificationRepository.save(notifications);
   }

   public List<HPCSchedule> returnSchedule(){
    return   hpcScheduleRepository.findAll();
    }
    public void MarkHPCSchedule(Long userId, String serialNumber){
       Optional<HPCSchedule> hpcSchedule = hpcScheduleRepository.findStateByUserIdAndItemId(userId,serialNumber);
       hpcSchedule.get().setState("Expired");

       hpcSchedule.get().getItems().setState("Available");
       hpcScheduleRepository.save(hpcSchedule.get());
    }

    public List<EquipmentRequests> returnEquipment(){
        return   equipmentRequestsRepository.getAllocated();
    }
    public void MarkEquipment(Long userId, String serialNumber){
       Optional<EquipmentRequests> equipmentRequests= equipmentRequestsRepository.findStateByUserIdAndItemId(userId, serialNumber);
       equipmentRequests.get().setState("Expired");
       equipmentRequests.get().getItemId().setState("Available");
       equipmentRequestsRepository.save(equipmentRequests.get());
    }
}


