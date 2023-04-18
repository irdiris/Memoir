package com.example.implmentation.Models.EquipmentRequests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EquipmentRequestsRepository extends JpaRepository<EquipmentRequests,EquipmentRequestsID> {
    @Modifying
    @Transactional
    @Query( value = "delete from equipment_requests where user_id = :id",nativeQuery = true)
    void deleteRequestByUserId(@Param("id") Long id);
}

