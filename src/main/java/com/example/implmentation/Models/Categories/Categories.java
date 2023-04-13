package com.example.implmentation.Models.Categories;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Resources.Resources;
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
@Table(name = "Categories", indexes = {@Index(name = "name", columnList = "name")})
public class Categories {
    @Id
    @SequenceGenerator(name = "Category_Sequence", sequenceName = "Category_Sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Category_Sequence")
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @OneToMany(mappedBy ="categories")
    private Set<Resources> resources;
    @OneToMany(mappedBy ="categories")
    private Set<Items> items;
}
