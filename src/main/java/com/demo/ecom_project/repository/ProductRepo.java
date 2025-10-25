package com.demo.ecom_project.repository;

import com.demo.ecom_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.name) LIKE :keyword OR " +
           "LOWER(p.description) LIKE :keyword OR " +
           "LOWER(p.brand) LIKE :keyword OR " +
           "LOWER(p.category) LIKE :keyword")
    List<Product> searchProducts(@Param("keyword") String keyword);

    List<Product> findByCategory(String category);
}