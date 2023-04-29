package com.example.implmentation.Models.Items;

import com.example.implmentation.Models.Categories.Categories;
import com.example.implmentation.Models.Purchases.Purchases;
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
@Table(name = "Items")
public class Items {

    @ManyToOne()
    @JoinColumn(name = "recieptId", referencedColumnName = "id", nullable = false)
    private Purchases purchases;
    private String name;
    @ManyToOne()
    @JoinColumn(  name = "type", referencedColumnName = "name")
    private Categories categories;
    @Id
    private String serialNumber;
    private String service;
    private String state;
    private String description;



}
