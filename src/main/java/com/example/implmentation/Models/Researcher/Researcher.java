package com.example.implmentation.Models.Researcher;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.HPCRequests.HPCRequests;
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
public class Researcher {
  @OneToOne(optional = false)
  @MapsId
  @Id
  @JoinColumn(name = "id",referencedColumnName = "id")
  private User user;
    private String type;
    @Column(name = "grade")
    private String rank;
    private String position;
    private String facility;


}
