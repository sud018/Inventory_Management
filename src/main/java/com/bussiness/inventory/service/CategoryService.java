package com.bussiness.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bussiness.inventory.model.Category;
import com.bussiness.inventory.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
            List<Category> categories = categoryRepository.findAll();
            return categories;
    }

    public Optional<Category> getCategoryById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category;
     }

    public Category createCategory(Category category){
        validateCategory(category);
        if(categoryRepository.existsByName(category.getName())){
            throw new RuntimeException("category with name " + category.getName() + " already exists");
        }
         return categoryRepository.save(category);
    }
   public Category updateCategory(Long id, Category category){
          Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found with id " + id));
          validateCategory(category);

          if(!existingCategory.getName().equals(category.getName())){
            throw new RuntimeException("category with name "+ category.getName() + " already exists");
          }
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        return categoryRepository.save(existingCategory);
   }
   public void deleteCategory(Long id){
     if(!categoryRepository.existsById(id)){
        throw new RuntimeException("category id doesnot exists");
     }
     Category category = categoryRepository.findById(id).get();
     if(category.getProducts()!=null && category.getProducts().isEmpty()){
        throw new RuntimeException("cannot delete category with existing products");
     }
     categoryRepository.deleteById(id);
   }
    private void validateCategory(Category category){
        if(category.getName() == null || category.getName().trim().isEmpty()){
            throw new IllegalArgumentException("category name is required");
        }
        if(category.getName().length() < 2){
            throw new IllegalArgumentException("category name should be atleast 2 characters");
        }
    }
}
