package com.example.implmentation.Models.HPCRequests;

import com.example.implmentation.Models.Items.Items;
import com.example.implmentation.Models.Researcher.Researcher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HPCRequests {
    private Researcher researcher;
    private Items item;
    private String dateOfAcquisition;
    private String dateOfReturn;
    private String Hours;
}
