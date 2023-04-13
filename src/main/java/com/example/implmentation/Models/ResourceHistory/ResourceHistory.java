package com.example.implmentation.Models.ResourceHistory;

import com.example.implmentation.Models.Resources.Resources;
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
@Table(name = "ResourceHistory")
public class ResourceHistory {

    private String name;
    private int quantity;
    private String service;
    private String dateOfAcquisition;
    @OneToOne(optional = false)
    @MapsId
    @Id
    private Resources resources;
}
