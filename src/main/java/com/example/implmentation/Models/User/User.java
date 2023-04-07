package com.example.implmentation.Models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
    private String adress;
    private int phone;
    private String username;
    private String email;
    private String password;
    private String type;

}
