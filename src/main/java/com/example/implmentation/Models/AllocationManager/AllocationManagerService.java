package com.example.implmentation.Models.AllocationManager;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
import com.example.implmentation.Models.HPCRequests.HPCRequests;
import com.example.implmentation.Models.HPCRequests.HPCRequestsRepository;
import com.example.implmentation.Models.HPCSchedule.HPCSchedule;
import com.example.implmentation.Models.HPCSchedule.HPCScheduleRepository;
import com.example.implmentation.Models.Notifications.NotificationRepository;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
import com.example.implmentation.Models.Student.Student;
import com.example.implmentation.Models.Student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Autowired
    public AllocationManagerService(StudentRepository studentRepository, ResearcherRepository researcherRepository, EquipmentRequestsRepository equipmentRequestsRepository, NotificationRepository notificationRepository, AllocationManagerRepository allocationManagerRepository, HPCRequestsRepository hpcRequestsRepository, HPCScheduleRepository hpcScheduleRepository) {
        this.studentRepository = studentRepository;
        this.researcherRepository = researcherRepository;
        this.equipmentRequestsRepository = equipmentRequestsRepository;
        this.notificationRepository = notificationRepository;
        this.allocationManagerRepository = allocationManagerRepository;
        this.hpcRequestsRepository = hpcRequestsRepository;
        this.hpcScheduleRepository = hpcScheduleRepository;
    }

    public List<EquipmentRequests> returnAllEquipmentRequests(){
        List<EquipmentRequests> equipmentRequestsList=
        equipmentRequestsRepository.findAll();
        return  equipmentRequestsList;
    }
    public void refuseEquipmentRequest (EquipmentRequests equipmentRequests){
       equipmentRequestsRepository.deleteRequestByUserIdandItemId(equipmentRequests.getUserId().getId(), equipmentRequests.getItemId().getSerialNumber());
        Notifications notifications = Notifications.builder()
                .notificationText("Your request for for item "+equipmentRequests.getItemId().getSerialNumber()+" has been denied.")
                .user(equipmentRequests.getUserId())
                .build();
        notificationRepository.save(notifications);
    }
 public void acceptEquipmentRequest(EquipmentRequests equipmentRequests){
        Optional<EquipmentRequests> equipmentRequestaPending= equipmentRequestsRepository.findStateByUserIdAndItemId(equipmentRequests.getUserId().getId(),equipmentRequests.getItemId().getSerialNumber());
        equipmentRequestaPending.get().setState("Allocated");
        equipmentRequestsRepository.save(equipmentRequestaPending.get());
     Notifications notifications = Notifications.builder()
             .notificationText("Your request for for item "+equipmentRequests.getItemId().getSerialNumber()+" has been approved. Head to the allocation service as soon as possible.")
             .user(equipmentRequests.getUserId())
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

    public void banStudent(Student student) {
        Optional<Student> studentOld = studentRepository.findById(student.getId());
        studentOld.get().getUser().setState("Banned");
        studentRepository.save(studentOld.get());

    }
    public void unbanStudent(Student student) {
        Optional<Student> studentOld = studentRepository.findById(student.getId());
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

    public void banResearcher(Researcher researcher) {
        Optional<Researcher> researcherOld = researcherRepository.findById(researcher.getId());
        researcherOld.get().getUser().setState("Banned");
        researcherRepository.save(researcherOld.get());
    }
    public void unbanResearcher(Researcher researcher) {
        Optional<Researcher> researcherOld = researcherRepository.findById(researcher.getId());
        researcherOld.get().getUser().setState("Banned");
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

   private List<HPCSchedule> returnHPCSchedule(){
        List<HPCSchedule> hpcScheduleList = hpcScheduleRepository.findAll();
       return hpcScheduleList;
   }
   private void refuseHPCRequest(HPCRequests hpcRequests){
        hpcRequestsRepository.deleteRequestByUserIdAndItemId(hpcRequests.getResearcher().getId(), hpcRequests.getItem().getSerialNumber());
       Notifications notifications = Notifications.builder()
               .notificationText("Your request for for item "+hpcRequests.getItem().getSerialNumber()+" has been denied.")
               .user(hpcRequests.getResearcher().getUser())
               .build();
       notificationRepository.save(notifications);
   }
   private void acceptHPCRequest(HPCRequests hpcRequests){
       hpcRequestsRepository.deleteRequestByUserIdAndItemId(hpcRequests.getResearcher().getId(), hpcRequests.getItem().getSerialNumber());
       HPCSchedule hpcSchedule= HPCSchedule.builder()
               .dateOfAcquisition(hpcRequests.getDateOfAcquisition())
               .dateOfReturn(hpcRequests.getDateOfReturn())
               .hours(hpcRequests.getHours())
               .items(hpcRequests.getItem())
               .researcher(hpcRequests.getResearcher())
               .state("Allocated")
               .build();
       hpcScheduleRepository.save(hpcSchedule);
       Notifications notifications = Notifications.builder()
               .notificationText("Your request for for item "+hpcRequests.getItem().getSerialNumber()+" has been accepted. you are free to use it at the assigned hours.")
               .user(hpcRequests.getResearcher().getUser())
               .build();
       notificationRepository.save(notifications);
   }
}

