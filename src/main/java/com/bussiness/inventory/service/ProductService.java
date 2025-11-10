package com.bussiness.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bussiness.inventory.model.Category;
import com.bussiness.inventory.model.Product;
import com.bussiness.inventory.repository.ProductRepository;
import com.bussiness.inventory.repository.CategoryRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }
 
    public List<Product> searchProduct(String name){
        return productRepository.searchByName(name);
    }

    public List<Product> getProductsByPriceRange(Double minPrice, Double maxPrice){
        if(minPrice<0 || maxPrice<0){
            throw new IllegalArgumentException("min and max price should be greater than 0");
        }
        if(minPrice>maxPrice){
            throw new IllegalArgumentException("min price should always be less than max price");
        }
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public List<Product> getLowStockProduct(Integer threshold){
        if(threshold<0){
            throw new IllegalArgumentException("Threshold shouldn't be negetive");
        }
        List<Product> LowStockProducts = productRepository.findLowStockPrice(threshold);
        if(!LowStockProducts.isEmpty()){
            System.out.println(LowStockProducts.size() + " are low in stock");
        }
        return LowStockProducts;
    }

    public List<Product> getOutOfStockProducts(){
        return productRepository.findOutOfStockProducts();
    }

    public List<Product> getPremiumStock(Double price){
         if(price<0){
            throw new IllegalArgumentException("price shouldn't be negetive");
         }
         return productRepository.findPremiumStock(price);
    }

    public Long getCount(){
        return productRepository.countProductInStock();
    }
    
    public Double totalInventory(){
        return productRepository.getTotalInventory();
    }

    public Product createProduct(Product product){
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product createProductWithCategory(Product product, Long categoryId){
                validateProduct(product);
                Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("category with Id not found: " + categoryId));
                product.setCategory(category);
                return productRepository.save(product);
            }
    public Product updateProduct(Long id, Product product){
        Product existingProduct = productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("product not found for id: " + id));

        validateProduct(existingProduct);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        if(product.getCategory()!=null){
            Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("category not found"));
        existingProduct.setCategory(category);
}
        return productRepository.save(existingProduct);
    }
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<Product> getProductByCategoryId(Long id){
           List<Product> product = productRepository.findByCategoryId(id);
           return product;
    }

    public Product assignCategoryToProduct(Long productId, Long categoryId){
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("product with id: " + productId + " not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("category with id: " + categoryId + " not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product createProductWithCategoryName(Product product, String categoryName){
        validateProduct(product);
        Category category = categoryRepository.findByName(categoryName).orElseGet(() -> {

            Category newCategory = new Category();
            newCategory.setName(categoryName);
            newCategory.setDescription("auto generated description for : "+ categoryName);
            return categoryRepository.save(newCategory);
        });

        product.setCategory(category);
        return productRepository.save(product);
    }
    private void validateProduct(Product product){
        if(product.getName()==null || product.getName().trim().isEmpty()){
            throw new IllegalArgumentException("product name is required");
        }
        if(product.getPrice() == null || product.getPrice()<0){
            throw new IllegalArgumentException("price is required and shouldn't be negetive");
        }
        if(product.getQuantity()==null || product.getQuantity()<0){
            throw new IllegalArgumentException("quantity is required and should't be null");
        }
    }
}
