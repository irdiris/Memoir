package com.example.implmentation.Models.EquipmentRequests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    private String itemId;
    private int userId;
    private String dateOfAcquisition;
}
