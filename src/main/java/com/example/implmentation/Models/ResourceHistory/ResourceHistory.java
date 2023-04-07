package com.example.implmentation.Models.ResourceHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceHistory {
    private Long id;
    private String name;
    private int quantity;
    private String service;
    private String dateOfAcquisition;
}
