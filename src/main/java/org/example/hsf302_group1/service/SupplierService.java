package org.example.hsf302_group1.service;

import org.example.hsf302_group1.entity.Supplier;
import org.example.hsf302_group1.repository.ProductRepository;
import org.example.hsf302_group1.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> findById(Integer id) {
        return supplierRepository.findById(id);
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void deleteById(Integer id) {
        try {
            supplierRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("This Supplier has reference products",e);
        }

    }
}