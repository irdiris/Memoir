package com.example.implmentation.Models.Student;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Items.ItemsRepository;
import com.example.implmentation.Models.Notifications.NotificationRepository;
import com.example.implmentation.Models.Notifications.Notifications;
import com.example.implmentation.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final EquipmentRequestsRepository equipmentRequestsRepository;
    private final NotificationRepository notificationRepository;
    private final ItemsRepository itemsRepository;
    private final StudentRepository studentRepository;
    private  final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public StudentService(EquipmentRequestsRepository equipmentRequestsRepository, NotificationRepository notificationRepository, ItemsRepository itemsRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {

        this.equipmentRequestsRepository = equipmentRequestsRepository;
        this.notificationRepository= notificationRepository;
        this.itemsRepository = itemsRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

public List<Items> getItemsForAllocation(){
    return itemsRepository.findItemsForAllocating();
}
public void requestEquipment(EquipmentRequests equipmentRequests){
        equipmentRequestsRepository.save(equipmentRequests);
}
public List<Notifications> getNotifications(Long id){
    return notificationRepository.getNotificationsById(id.intValue());

}

public List<EquipmentRequests> getHistory(Long id){
    return equipmentRequestsRepository.getHistory(id);
}
public void UpdateProfile(Student student, String username){
        Optional<Student> optionalStudent =studentRepository.findById(userRepository.getUserByUsername(username).get().getId());
        optionalStudent.get().setSpecialty(student.getSpecialty());
    optionalStudent.get().setInstitution(student.getInstitution());
    optionalStudent.get().setLevel(student.getLevel());
    optionalStudent.get().setSpecialty(student.getSpecialty());
    optionalStudent.get().getUser().setAddress(student.getUser().getAddress());
    optionalStudent.get().getUser().setPassword(passwordEncoder.encode(student.getUser().getPassword()));
    optionalStudent.get().getUser().setUsername(student.getUser().getUsername());
    optionalStudent.get().getUser().setEmail(student.getUser().getEmail());
    optionalStudent.get().getUser().setFirstName(student.getUser().getFirstName());
    optionalStudent.get().getUser().setLastName(student.getUser().getLastName());
    optionalStudent.get().getUser().setPhone(student.getUser().getPhone());







}
}
