package com.example.implmentation.Models.AllocationManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationManager {
    private Long id;
    private String type;
    private String status;
}
