package org.example.hsf302_group1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String productid;

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    @Min(0)
    private double price;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String createdBy;
}
