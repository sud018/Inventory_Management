package com.bussiness.inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bussiness.inventory.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String Email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String emailHash);

    @Query("select u from User u")
    List<User> findAllUsers();


}
