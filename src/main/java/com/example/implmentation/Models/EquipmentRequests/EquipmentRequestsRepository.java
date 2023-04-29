package com.example.implmentation.Models.EquipmentRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRequestsRepository extends JpaRepository<EquipmentRequests,EquipmentRequestsID> {
    @Modifying
    @Transactional
    @Query( value = "delete from equipment_requests where user_id = :userId and item_id = :itemId",nativeQuery = true)
    void deleteRequestByUserIdandItemId(@Param("userId") Long userId, @Param("itemId") String itemId);
   @Query( value = "select state from equipment_requests where item_id =:itemId and  user_id= :userId",nativeQuery = true)
    Optional<EquipmentRequests> findStateByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") String itemId);
    @Query( value = "select * from equipment_requests where  state='Expired' user_id= :userId",nativeQuery = true)
    List<EquipmentRequests> getHistory(@Param("userId") Long userId);
}

