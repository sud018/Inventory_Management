package com.bussiness.inventory.controller;

import com.bussiness.inventory.model.Product;
import com.bussiness.inventory.service.ProductService;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        if(!products.isEmpty()){
          return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id){
         Optional<Product> product = productService.getProduct(id);
          if(product.isPresent()){
            return ResponseEntity.ok(product.get());
          }
          else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
          }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String name){
        List<Product> products = productService.searchProduct(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice){
      List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
      return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProduct(@RequestParam Integer threshold){
      List<Product> products = productService.getLowStockProduct(threshold);
      return ResponseEntity.ok(products);
    }
    
    @GetMapping("/out-of-stock")
    public ResponseEntity<List<Product>> getOutOfStockProducts(){
      List<Product> products = productService.getOutOfStockProducts();
      return ResponseEntity.ok(products);
    }

    @GetMapping("/premium-stock")
    public ResponseEntity<List<Product>> getPremiumStock(@RequestParam Double price){
      List<Product> products = productService.getPremiumStock(price);
      return ResponseEntity.ok(products);
    }

    @GetMapping("/count-stock")
    public ResponseEntity<Long> getCount(){
      Long count = productService.getCount();
      return ResponseEntity.ok(count);
    }

    @GetMapping("/getTotalInventory")
    public ResponseEntity<Double> getTotalInventory(){
      Double total = productService.totalInventory();
      return ResponseEntity.ok(total);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product){
          productService.createProduct(product);
    }

    @PostMapping(params = "categoryId")
    public ResponseEntity<?> createProductWithCategory(@RequestBody Product product, @RequestParam Long categoryId){
           try{
            Product createdProduct = productService.createProductWithCategory(product, categoryId);
            return ResponseEntity.ok(createdProduct);
           }
           catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
           }
    }
    @PostMapping(params = "categoryName")
    public ResponseEntity<?> createProductWithCategoryName(@RequestParam String categoryName, @RequestBody Product product){
      try{
        Product createdProduct = productService.createProductWithCategoryName(product, categoryName);
        return ResponseEntity.ok(createdProduct);
      }
      catch(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

      @PutMapping("/{id}")
      public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product){
        try{
       Product updatedproduct = productService.updateProduct(id, product);
       return ResponseEntity.ok(updatedproduct);
        }
        catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> assignCategoryToProduct(@PathVariable Long productId, @PathVariable Long categoryId){
      try{
        Product product = productService.assignCategoryToProduct(productId, categoryId);
        return ResponseEntity.ok(product);
      }
      catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
     @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
      try{
        productService.deleteProduct(id);
        return ResponseEntity.ok("deleted Successfully");
      }
      catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId){
      List<Product> products = productService.getProductByCategoryId(categoryId);
      return ResponseEntity.ok(products);
    }
}
