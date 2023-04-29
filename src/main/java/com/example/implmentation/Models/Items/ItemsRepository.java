package com.example.implmentation.Models.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemsRepository  extends JpaRepository<Items, Long> {

    @Query (value = "select * from items where state='Available' and service= 'Allocation service'",nativeQuery = true)
    List<Items> findItemsForAllocating();
}
