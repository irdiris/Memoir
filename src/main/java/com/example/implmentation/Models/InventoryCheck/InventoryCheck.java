package com.example.implmentation.Models.InventoryCheck;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Purchases.Purchases;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "InventoryCheck")
public class InventoryCheck {
    @Id
    @SequenceGenerator(name = "inventory_check_Sequence", sequenceName = "inventory_check_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_check_Sequence")
    private Long id;

 private String status;
 private String DateOfCheck;
    private String name;
    private String LastSeen;

    @ManyToOne()
    @JoinColumn(name = "item", referencedColumnName = "serialNumber", nullable = false)
    private Items item;



}
