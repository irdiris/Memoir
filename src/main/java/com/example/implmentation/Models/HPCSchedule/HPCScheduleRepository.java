package com.example.implmentation.Models.HPCSchedule;

import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.Researcher.Researcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HPCScheduleRepository  extends JpaRepository<HPCSchedule, HPCScheduleId> {
    @Query( value = "select * from hpcschedule  where item_id =:itemId and  user_id= :userId",nativeQuery = true)
    Optional<HPCSchedule> findStateByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") String itemId);
List<HPCSchedule> findByResearcher(Researcher researcher);

}
