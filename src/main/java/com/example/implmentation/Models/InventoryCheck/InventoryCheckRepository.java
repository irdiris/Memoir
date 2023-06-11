package com.example.implmentation.Models.InventoryCheck;

import com.example.implmentation.Models.Items.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryCheckRepository extends JpaRepository<InventoryCheck, Long> {
    boolean existsByItem(Items items);
@Transactional
@Modifying
    void deleteByItem(Items items);
}
