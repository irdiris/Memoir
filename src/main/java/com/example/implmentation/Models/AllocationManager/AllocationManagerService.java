package com.example.implmentation.Models.AllocationManager;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
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


    @Autowired
    public AllocationManagerService(StudentRepository studentRepository, ResearcherRepository researcherRepository, EquipmentRequestsRepository equipmentRequestsRepository, NotificationRepository notificationRepository, AllocationManager allocationManager, AllocationManagerRepository allocationManagerRepository) {
        this.studentRepository = studentRepository;
        this.researcherRepository = researcherRepository;
        this.equipmentRequestsRepository = equipmentRequestsRepository;
        this.notificationRepository = notificationRepository;

        this.allocationManagerRepository = allocationManagerRepository;
    }

    public List<EquipmentRequests> returnAllEquipmentRequests(){
        List<EquipmentRequests> equipmentRequestsList=
        equipmentRequestsRepository.findAll();
        return  equipmentRequestsList;
    }
    public void refuseEquipmentRequest (EquipmentRequests equipmentRequests){
       equipmentRequestsRepository.deleteRequestByUserId(equipmentRequests.getUserId().getId(), equipmentRequests.getItemId().getSerialNumber());
        Notifications notifications = Notifications.builder()
                .notificationText("Your request for for item "+equipmentRequests.getItemId().getSerialNumber()+" has been denied.")
                .user(equipmentRequests.getUserId())
                .build();
    }
 public void acceptEquipmentRequest(EquipmentRequests equipmentRequests){
        Optional<EquipmentRequests> equipmentRequestaPending= equipmentRequestsRepository.findByUserId(equipmentRequests.getUserId().getId());
        equipmentRequestaPending.get().setState("Allocated");
        equipmentRequestsRepository.save(equipmentRequestaPending.get());
     Notifications notifications = Notifications.builder()
             .notificationText("Your request for for item "+equipmentRequests.getItemId().getSerialNumber()+" has been approved. Head to the allocation service as soon as possible.")
             .user(equipmentRequests.getUserId())
             .build();
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
       allocationManagerOld.get().getUser().setAdress(allocationManager.getUser().getAdress());
       allocationManagerOld.get().getUser().setEmail(allocationManager.getUser().getEmail());
       allocationManagerOld.get().getUser().setFirstName(allocationManager.getUser().getFirstName());
       allocationManagerOld.get().getUser().setLastName(allocationManager.getUser().getLastName());
       allocationManagerOld.get().getUser().setPhone(allocationManager.getUser().getPhone());
       allocationManagerOld.get().getUser().setUsername(allocationManager.getUser().getUsername());
       allocationManagerOld.get().setStatus(allocationManager.getStatus());
   }
}

