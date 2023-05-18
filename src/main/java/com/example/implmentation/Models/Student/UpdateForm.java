package com.example.implmentation.Models.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateForm {
    private  Long id;
    private String specialty;
    private String level;
    private String type;
    private String institution;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String Address;
    private int phone;
    private String username;
    private String email;
    private String password;
    private String  state;
}
