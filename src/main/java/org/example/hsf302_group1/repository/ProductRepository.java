package org.example.hsf302_group1.repository;

import org.example.hsf302_group1.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT TOP 5 * FROM products ORDER BY price DESC", nativeQuery = true)
    List<Product> findTop5ByOrderByPriceDesc();
}