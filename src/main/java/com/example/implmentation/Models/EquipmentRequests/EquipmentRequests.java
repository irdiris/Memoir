package com.example.implmentation.Models.EquipmentRequests;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
import com.example.implmentation.Models.Student.Student;
import com.example.implmentation.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "EquipmentRequests")

public class EquipmentRequests {
   @ManyToOne
   @Id
   @MapsId("id")
   @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_user"))
   private User user;
    @ManyToOne
    @Id
    @JoinColumn(name = "itemId",foreignKey = @ForeignKey(name = "fk_item"), referencedColumnName = "serialNumber")
    private Items items;
    private String userType;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String state;


}
