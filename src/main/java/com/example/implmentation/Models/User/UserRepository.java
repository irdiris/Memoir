package com.example.implmentation.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> getUserByUsername(String username);

    @Query( value = "select type from user where username= :username",nativeQuery = true)
    String getTypeByUsername(@Param("username") String username);
}
