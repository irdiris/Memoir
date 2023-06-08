package com.example.implmentation.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> getUserByUsername(String username);
    Boolean existsByIdAndPassword(Long id, String password);

    @Query( value = "select type from user where username= :username",nativeQuery = true)
    String getTypeByUsername(@Param("username") String username);
    @Query( value = "select * from user where type='Allocation Manager' OR type='Inventory Manager'",nativeQuery = true)
    List<User> getManagers();
    @Query( value = "select count(id) from user where type= :type",nativeQuery = true)
    int getUsers(@Param("type") String type);
}
