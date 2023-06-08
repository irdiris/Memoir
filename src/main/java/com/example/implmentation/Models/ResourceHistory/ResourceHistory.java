package com.example.implmentation.Models.ResourceHistory;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsID;
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

    @Id
    @SequenceGenerator(name = "history_Sequence", sequenceName = "history_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_Sequence")
    private Long id;

    private String name;
    private int quantity;
    private String service;
    private String dateOfAcquisition;

    @OneToOne(optional = false)
    @JoinColumn(name = "resource_id")
    private Resources resources;
}
