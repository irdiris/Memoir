package com.example.implmentation.Models.ResourceHistory;

import com.example.implmentation.Models.Resources.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceHistoryRepository  extends JpaRepository<ResourceHistory, Long> {
}
