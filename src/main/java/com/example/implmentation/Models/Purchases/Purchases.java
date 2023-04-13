package com.example.implmentation.Models.Purchases;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Resources.Resources;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "purchases")
public class Purchases {
@Id
    private Long id;
    private String supplier;
    private String description;
    private String date;
    @OneToMany(mappedBy = "purchases")
    private Set<Resources>  resources;
    @OneToMany(mappedBy = "purchases")
    private Set<Items>  items;
}
