package com.example.implmentation.Models.AllocationManager;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
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


    @Autowired
    public AllocationManagerService(StudentRepository studentRepository, ResearcherRepository researcherRepository, EquipmentRequestsRepository equipmentRequestsRepository) {
        this.studentRepository = studentRepository;
        this.researcherRepository = researcherRepository;
        this.equipmentRequestsRepository = equipmentRequestsRepository;
    }

    public void refuseEquipmentRequest (Long id){
       equipmentRequestsRepository.deleteRequestByUserId(id);

    }

    public void deleteStudent(int id) {
        studentRepository.deleteById((long) id);
    }

    public List<Student> returnAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return studentList;
    }

    public void updateStudent(Student student) {
        Optional<Student> studentOld = studentRepository.findById(student.getId());
        studentOld.get().setInstitution(student.getInstitution());
        studentOld.get().setLevel(student.getLevel());
        studentOld.get().setSpecialty(student.getSpecialty());
        studentOld.get().getUser().setFirstName(student.getUser().getFirstName());
        studentOld.get().getUser().setLastName(student.getUser().getLastName());
        studentOld.get().getUser().setAdress(student.getUser().getAdress());
        studentOld.get().getUser().setEmail(student.getUser().getEmail());
        studentOld.get().getUser().setBirthDate(student.getUser().getBirthDate());
        studentOld.get().getUser().setGender(student.getUser().getGender());
        studentOld.get().getUser().setPhone(student.getUser().getPhone());
        studentOld.get().getUser().setUsername(student.getUser().getUsername());
        studentOld.get().getUser().setPassword(student.getUser().getPassword());
        studentRepository.save(studentOld.get());

    }



    public void deleteResearcher(int id) {
        researcherRepository.deleteById((long) id);
    }

    public List<Researcher> returnAllResearchers() {
        List<Researcher> researcherList = researcherRepository.findAll();
        return researcherList;
    }

    public void updateResearcher(Researcher researcher) {
        Optional<Researcher> researcherOld = researcherRepository.findById(researcher.getId());
        researcherOld.get().setFacility(researcher.getFacility());
        researcherOld.get().setRank(researcher.getRank());
        researcherOld.get().setPosition(researcher.getPosition());
        researcherOld.get().getUser().setFirstName(researcher.getUser().getFirstName());
        researcherOld.get().getUser().setLastName(researcher.getUser().getLastName());
        researcherOld.get().getUser().setAdress(researcher.getUser().getAdress());
        researcherOld.get().getUser().setEmail(researcher.getUser().getEmail());
        researcherOld.get().getUser().setBirthDate(researcher.getUser().getBirthDate());
        researcherOld.get().getUser().setGender(researcher.getUser().getGender());
        researcherOld.get().getUser().setPhone(researcher.getUser().getPhone());
        researcherOld.get().getUser().setUsername(researcher.getUser().getUsername());
        researcherOld.get().getUser().setPassword(researcher.getUser().getPassword());
        researcherRepository.save(researcherOld.get());
    }

}

