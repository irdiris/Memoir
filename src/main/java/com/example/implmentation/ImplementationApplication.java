package com.example.implmentation;

import com.example.implmentation.Models.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ImplementationApplication {



    public static void main(String[] args) {


        SpringApplication.run(ImplementationApplication.class, args);

        System.out.println("http://localhost:8080");


    }}


