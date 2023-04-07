package com.example.implmentation.Models.Resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resources {
    private Long id;
    private String type;
    private int receiptId;
    private String name;
    private int quantity;
}
