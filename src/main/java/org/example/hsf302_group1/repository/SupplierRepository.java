package org.example.hsf302_group1.repository;

import org.example.hsf302_group1.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}