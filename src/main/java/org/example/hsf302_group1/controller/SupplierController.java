package org.example.hsf302_group1.controller;

import jakarta.validation.Valid;
import org.example.hsf302_group1.entity.Supplier;
import org.example.hsf302_group1.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliers(Model model ) {
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("supplier", new Supplier());
        return "suppliers";
    }

    @PostMapping("/suppliers/add")
    public String addSupplier(@Valid @ModelAttribute Supplier supplier,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierService.findAll());
            return "suppliers";
        }
        supplierService.save(supplier);
        return "redirect:/suppliers";
    }

    @PostMapping("/suppliers/update")
    public String updateSupplier(@Valid @ModelAttribute Supplier supplier,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierService.findAll());
            return "suppliers";
        }
        supplierService.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            supplierService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "✅ Supplier deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Cannot delete supplier: This supplier has reference products.");
        }
        return "redirect:/suppliers";
    }


    @GetMapping("/suppliers/edit/{id}")
    public String editSupplier(@PathVariable Integer id, Model model) {
        supplierService.findById(id).ifPresent(s -> model.addAttribute("supplier", s));
        model.addAttribute("suppliers", supplierService.findAll());
        return "suppliers";
    }
}