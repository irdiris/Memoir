package com.example.implmentation.Models.Items;

import com.example.implmentation.Models.Categories.Categories;
import com.example.implmentation.Models.InventoryCheck.InventoryCheck;
import com.example.implmentation.Models.Purchases.Purchases;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Items")
public class Items {

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "recieptId", referencedColumnName = "id", nullable = false)
    private Purchases purchases;
    private String name;
    @ManyToOne()
    @JsonIgnore
    @JoinColumn(  name = "type", referencedColumnName = "name")
    private Categories categories;
    @Id
    private String serialNumber;
    private String service;
    private String state;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Set<InventoryCheck> inventoryCheck;



}
