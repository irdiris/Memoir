package com.example.implmentation.Models.InventoryManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryManager {
    private Long id;
    private String type;
    private String status;


}
