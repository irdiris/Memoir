package com.example.implmentation.Models.EquipmentRequests;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentRequests {
    private Student student;
    private Items item;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String state;


}
