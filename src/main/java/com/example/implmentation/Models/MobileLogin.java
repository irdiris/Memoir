package com.example.implmentation.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MobileLogin {
    private Long id;
    private String password;
}
