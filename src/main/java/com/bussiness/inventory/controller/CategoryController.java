package com.bussiness.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bussiness.inventory.model.Category;
import java.util.List;
import java.util.Optional;

import com.bussiness.inventory.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/id")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
         Optional<Category> category = categoryService.getCategoryById(id);
         if(category.isPresent()){
         return ResponseEntity.ok(category.get());
         }
         else{
            return ResponseEntity.notFound().build();
         }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
       try{
        Category savedcategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedcategory);
       }
       catch(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category catgory){
        try{
            Category updatedCategory = categoryService.updateCategory(id, catgory);
            return ResponseEntity.ok(updatedCategory);
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
           try{
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("category deleted successsfully");
           }
           catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
           }
    }
}
