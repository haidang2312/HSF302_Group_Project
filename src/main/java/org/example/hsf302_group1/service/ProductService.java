package org.example.hsf302_group1.service;

import org.example.hsf302_group1.entity.Product;
import org.example.hsf302_group1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // DÀNH CHO STAFF: tất cả + phân trang
    public Page<Product> findAllPaginated(int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        return productRepository.findAll(pageable);
    }

    // DÀNH CHO MANAGER: CHỈ 5 GIÁ CAO NHẤT
    public List<Product> findTop5ByPriceDesc() {
        return productRepository.findTop5ByOrderByPriceDesc(); // KHÔNG CẦN .getContent()
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Product save(Product product, String username) {
        if (product.getId() == 0) {
            product.setCreatedAt(LocalDate.now());
            product.setCreatedBy(username);
        }
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product);
    }

    public Optional<Product> updateIfOwner(Product product, String username) {
        return productRepository.findById(product.getId()).map(existing -> {
            if (!existing.getCreatedBy().equals(username)) {
                return null;
            }
            product.setCreatedAt(existing.getCreatedAt());
            product.setCreatedBy(existing.getCreatedBy());
            product.setUpdatedAt(LocalDate.now());
            return productRepository.save(product);
        });
    }

    public boolean deleteIfOwner(int id, String username) {
        return productRepository.findById(id).map(p -> {
            if (p.getCreatedBy().equals(username)) {
                productRepository.deleteById(id);
                return true;
            }
            return false;
        }).orElse(false);
    }
}