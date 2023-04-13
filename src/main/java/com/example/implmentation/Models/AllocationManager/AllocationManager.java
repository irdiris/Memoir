package com.example.implmentation.Models.AllocationManager;

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
@Table(name = "AllocationManager")
public class AllocationManager {
    @OneToOne(optional = false)
    @MapsId
    @Id
    @JoinColumn(name = "id",referencedColumnName = "id")
    private User user;
    private String type;
    private String status;
}
