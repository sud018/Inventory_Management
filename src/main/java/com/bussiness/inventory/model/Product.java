package com.bussiness.inventory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    
    // public Product(){};
    // public Product(Long id, String name, Double price, Integer quantity){
    //     this.id = id;
    //     this.name=name;
    //     this.price=price;
    //     this.quantity=quantity;
    // }

    // public Long getId(){return this.id;}
    // public void setId(Long id){this.id=id;}

    // public String getName(){return this.name;}
    // public void setName(String name){this.name=name;}

    // public Double getPrice(){return this.price;}
    // public void setPrice(Double price){this.price=price;}

    // public Integer getQuantity(){return this.quantity;}
    // public void setQuantity(Integer quantity){this.quantity = quantity;}

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonBackReference
    private Category category;

}
