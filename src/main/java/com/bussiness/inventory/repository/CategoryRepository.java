package com.bussiness.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bussiness.inventory.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
  
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
    
}
