package com.example.implmentation.Models.Resources;

import com.example.implmentation.Models.ResourceHistory.ResourceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {
}
