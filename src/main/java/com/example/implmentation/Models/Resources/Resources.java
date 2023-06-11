package com.example.implmentation.Models.Resources;

import com.example.implmentation.Models.Categories.Categories;
import com.example.implmentation.Models.Purchases.Purchases;
import com.example.implmentation.Models.ResourceHistory.ResourceHistory;
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
@Table(name = "Resources")
public class Resources {
    @Id
    private Long id;
    @ManyToOne()
    @JoinColumn(  name = "type", referencedColumnName = "name")
    private Categories categories;
   @ManyToOne()
   @JoinColumn(name = "recieptId", referencedColumnName = "id", nullable = false)
   private Purchases purchases;
    private String name;
    private int quantity;

    @OneToMany(mappedBy = "resources")
    private Set<ResourceHistory> resourceHistory;
}
