package com.example.implmentation.Models.Purchases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepository  extends JpaRepository<Purchases, Long> {
}
