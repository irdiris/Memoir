package com.example.implmentation.Models.InventoryCheck;

import com.example.implmentation.Models.Items.Items;
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
@Table(name = "Inventory_check")
public class InventoryCheck {
    @Id
    @SequenceGenerator(name = "inventoryCheck_Sequence", sequenceName = "InventoryCheck_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "InventoryCheck_Sequence")
    private int id;
    private String status;
    @ManyToOne
    @JoinColumn(name = "item", foreignKey = @ForeignKey(name = "fk_item_Not"), referencedColumnName = "serialNumber")
    private Items item;
    private String InspectionDate;
}
