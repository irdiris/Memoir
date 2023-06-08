package com.example.implmentation.Models.InventoryCheck;

import com.example.implmentation.Models.Items.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryCheckRepository extends JpaRepository<InventoryCheck, Long> {
    boolean existsByItem(Items items);

    void deleteByItem(Items items);
}
