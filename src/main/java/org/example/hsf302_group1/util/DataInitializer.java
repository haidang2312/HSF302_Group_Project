package org.example.hsf302_group1.util;

import jakarta.annotation.PostConstruct;
import org.example.hsf302_group1.entity.Product;
import org.example.hsf302_group1.entity.Supplier;
import org.example.hsf302_group1.entity.UserAccount;
import org.example.hsf302_group1.repository.ProductRepository;
import org.example.hsf302_group1.repository.SupplierRepository;
import org.example.hsf302_group1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void initData() {
        // --- 1️⃣ Tạo tài khoản mẫu ---
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                    new UserAccount(null, "manager1", "123", 1),
                    new UserAccount(null, "staff1", "123", 2),
                    new UserAccount(null, "staff2", "123", 2),
                    new UserAccount(null, "guest1", "123", 3)
            ));
        }

        // --- 2️⃣ Tạo 5 Supplier ---
        if (supplierRepository.count() == 1) {
            List<Supplier> suppliers = supplierRepository.saveAll(List.of(
                    Supplier.builder().supplierName("Supplier Alpha").build(),
                    Supplier.builder().supplierName("Supplier Beta").build(),
                    Supplier.builder().supplierName("Supplier Gamma").build(),
                    Supplier.builder().supplierName("Supplier Delta").build(),
                    Supplier.builder().supplierName("Supplier Omega").build()
            ));

            // --- 3️⃣ Tạo 20 sản phẩm (4 sản phẩm mỗi supplier) ---
            int count = 1;
            for (Supplier s : suppliers) {
                for (int i = 1; i <= 4; i++) {
                    productRepository.save(Product.builder()
                            .productid("P" + String.format("%03d", count))
                            .name("Product " + count + " from " + s.getSupplierName())
                            .price(50.0 + (count * 10))
                            .supplier(s)
                            .createdAt(LocalDate.now().minusDays(count))
                            .updatedAt(LocalDate.now())
                            .createdBy(count % 2 == 0 ? "staff1" : "staff2")
                            .build());
                    count++;
                }
            }
        }
    }
}
