package com.example.implmentation.Models.Items;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private Long id;
    private Long receiptId;
    private String name;
    private String type;
    private String serialNumber;
    private String location;
    private String state;
    private String description;


}
