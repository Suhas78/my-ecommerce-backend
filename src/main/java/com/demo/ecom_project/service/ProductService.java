package com.demo.ecom_project.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.ecom_project.model.Product;
import com.demo.ecom_project.repository.ProductRepo;

@Service
public class ProductService {
    @Autowired
    ProductRepo repo;
    public List<Product>getAllProducts(){
        return repo.findAll();
    }

    public Product getProductByID(int proid){
       return repo.findById(proid).orElse(null);
    }

    public Product postProducts(Product product,MultipartFile imagFile) throws IOException{
        //we have to send the imgage also.
        product.setImageName(imagFile.getOriginalFilename());
        product.setImageType(imagFile.getContentType());
        product.setImageData(imagFile.getBytes());
        return repo.save(product);
    }



    // public Product updateProduct(int id,Product product,MultipartFile imageFile) throws IOException{
    //     product.setImageData(imageFile.getBytes());
    //     product.setImageName(imageFile.getOriginalFilename());
    //     product.setImageType(imageFile.getContentType());
    //   return repo.save(product);
    // }
    // THIS IS THE CORRECT VERSION
public Product updateProduct(int id, Product updatedProductData, MultipartFile imageFile) throws IOException {
    // Step 1: Find the existing product in the database.
    Product existingProduct = repo.findById(id).orElse(null);

    // Step 2: If it exists, update its fields with the new data.
    if (existingProduct != null) {
        existingProduct.setName(updatedProductData.getName());
        existingProduct.setDescription(updatedProductData.getDescription());
        existingProduct.setBrand(updatedProductData.getBrand());
        existingProduct.setPrice(updatedProductData.getPrice());
        existingProduct.setCategory(updatedProductData.getCategory());
        existingProduct.setReleaseDate(updatedProductData.getReleaseDate());
        existingProduct.setProductAvailable(updatedProductData.isProductAvailable());
        existingProduct.setStockQuantity(updatedProductData.getStockQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageData(imageFile.getBytes());
        }

        // Step 3: Save the updated existing product.
        return repo.save(existingProduct);
    }

    // Return null if no product was found to update.
    return null; 
}


     public void deleteProducts(int proid){
        repo.deleteById(proid);
    }


    public List<Product> searchProducts(String keyword){
        String formattedKeyword = "%" + keyword.toLowerCase() + "%";
     return repo.searchProducts(formattedKeyword);
    }


    public List<Product> getProductsByCategory(String category) {
       return repo.findByCategory(category);
    }


}