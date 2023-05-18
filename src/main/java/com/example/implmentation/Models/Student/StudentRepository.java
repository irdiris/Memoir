package com.example.implmentation.Models.Student;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long > {

}
