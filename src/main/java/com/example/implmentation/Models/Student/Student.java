package com.example.implmentation.Models.Student;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.User.User;
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
@Table(name = "Student")
public class Student {
    @Id
    private  Long id;
    private String specialty;
    private String level;
    private String type;
    private String institution;

    @OneToOne( cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "id",referencedColumnName = "id")
    @MapsId
    private User user;

}
