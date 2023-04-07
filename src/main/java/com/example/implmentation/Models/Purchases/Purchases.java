package com.example.implmentation.Models.Purchases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchases {
    private Long id;
    private String supplier;
    private String description;
    private String date;
}
