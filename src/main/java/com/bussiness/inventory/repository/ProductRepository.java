package com.bussiness.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bussiness.inventory.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategoryId(Long categoryId);

    @Query("select p from Product p where LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> searchByName(@Param("name") String name);

    @Query("select p from Product p where p.price BETWEEN :minPrice and :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("select p from Product p where p.quantity < :threshold")
    List<Product> findLowStockPrice(@Param("threshold") Integer threshold);

    @Query("select p from Product p where p.quantity=0")
    List<Product> findOutOfStockProducts();

    @Query("select p from Product p where p.quantity>0 and (p.price/p.quantity) > :price")
    List<Product> findPremiumStock(@Param("price") Double price);

    @Query("select count(p) from Product p where p.quantity>0")
    Long countProductInStock();

    @Query("select SUM(p.price * p.quantity) from Product p")
    Double getTotalInventory();
}
