package com.example.implmentation.Models.Researcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Researcher {
    private Long id;
    private String type;
    private String rank;
    private String position;
    private String facility;
}
