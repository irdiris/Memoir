package com.example.implmentation.Models.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
    private String specialty;
    private String level;
    private String type;
    private String institution;
}
