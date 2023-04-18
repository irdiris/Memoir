package com.example.implmentation;

import com.example.implmentation.Models.AllocationManager.AllocationManagerService;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequests;
import com.example.implmentation.Models.EquipmentRequests.EquipmentRequestsRepository;
import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Purchases.Purchases;
import com.example.implmentation.Models.Researcher.ResearcherRepository;
import com.example.implmentation.Models.Student.Student;

import com.example.implmentation.Models.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ImplmentationApplication {
      private static AllocationManagerService allocationManagerService;

@Autowired
    public ImplmentationApplication(AllocationManagerService allocationManagerService) {
        this.allocationManagerService= allocationManagerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImplmentationApplication.class, args);

        Items item = Items.builder()
                .serialNumber("1")
                .purchases(Purchases.builder().id(1L).build())
                .build();
EquipmentRequests equipmentRequests= EquipmentRequests.builder()
        .itemId(item)
        .userId(User.builder().id(5L).build())
        .build();

        allocationManagerService.refuseEquipmentRequest(5L);
        }
    }


