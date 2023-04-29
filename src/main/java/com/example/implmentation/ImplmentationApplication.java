package com.example.implmentation;

import com.example.implmentation.Models.User.UserRepository;
import com.example.implmentation.Security.CustomUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ImplmentationApplication {



    public static void main(String[] args) {


        SpringApplication.run(ImplmentationApplication.class, args);

        System.out.println("http://localhost:8080/");


    }}


