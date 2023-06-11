package com.example.implmentation.Models.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemsRepository  extends JpaRepository<Items, Long> {

    @Query (value = "select * from items where state='Available' and service= 'Allocation service'",nativeQuery = true)
    List<Items> findItemsForAllocating();
    @Query (value = "select * from items where state='Available' and service= 'Allocation service' and type='HPC' ",nativeQuery = true)
    List<Items> findHPCForAllocating();
    @Query (value = "select * from items where serial_number = :serial_number",nativeQuery = true)
    Items findItems(@Param("serial_number") String serial_number);
    @Query( value = "select count(serial_number) from items ",nativeQuery = true)
    int getItems();
    @Query( value = "select count(serial_number) from items where state = :state ",nativeQuery = true)
    int getItemCount(@Param("state") String state);
    @Query( value = "select count(serial_number) from items where type = :type ",nativeQuery = true)
    int getTypeCount(@Param("type") String state);
    @Transactional
    @Modifying
    void deleteBySerialNumber(String serialNumber);
}
