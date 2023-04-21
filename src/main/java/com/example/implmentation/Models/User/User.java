package com.example.implmentation.Models.User;

import com.example.implmentation.Models.AllocationManager.AllocationManager;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.InventoryManager.InventoryManager;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "User")
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String adress;
    private int phone;
    private String username;
    private String email;
    private String password;
    private String type;
    private String  state;

    @OneToOne(mappedBy = "user")

    private Student student;
    @OneToOne(mappedBy = "user")

    private Researcher researcher;
    @OneToOne(mappedBy = "user")

    private AllocationManager allocationManager;
    @OneToOne(mappedBy = "user")

    private InventoryManager  inventoryManager;
    @OneToMany(mappedBy = "userId")
    private Set<EquipmentRequests> equipmentRequests;


}
