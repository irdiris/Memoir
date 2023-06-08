package com.example.implmentation.Models.HPCRequests;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HPCRequestsRepository extends JpaRepository<HPCRequests, HPCRequestsId> {
    @Modifying
    @Transactional
    @Query( value = "delete from hpcrequests where user_id = :userId and item_id = :itemId",nativeQuery = true)
    void deleteRequestByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") String itemId);


}
