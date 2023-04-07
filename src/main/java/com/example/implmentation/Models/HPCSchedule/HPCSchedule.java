package com.example.implmentation.Models.HPCSchedule;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HPCSchedule {
    private Researcher researcher;
    private Items item;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String hours;
    private String state;

}
