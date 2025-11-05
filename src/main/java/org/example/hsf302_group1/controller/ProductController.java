package org.example.hsf302_group1.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.hsf302_group1.entity.Product;
import org.example.hsf302_group1.service.ProductService;
import org.example.hsf302_group1.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/products")
    public String products(HttpSession session, Model model,
                           @RequestParam(defaultValue = "0") int page) {
        String username = (String) session.getAttribute("username");
        Integer roleObj = (Integer) session.getAttribute("role");

        if (username == null || roleObj == null) {
            return "redirect:/";
        }

        int role = roleObj;
        model.addAttribute("username", username);
        model.addAttribute("isManager", role == 1);

        if (role == 1) {
            // MANAGER: CHỈ XEM 5 SẢN PHẨM GIÁ CAO NHẤT
            model.addAttribute("top5Products", productService.findTop5ByPriceDesc());
            // KHÔNG LOAD suppliers, product, productPage → ẨN FORM + PHÂN TRANG
        } else {
            // STAFF: xem tất cả + phân trang
            model.addAttribute("suppliers", supplierService.findAll());
            model.addAttribute("product", new Product());
            Page<Product> productPage = productService.findAllPaginated(page);
            model.addAttribute("productPage", productPage);
            model.addAttribute("currentPage", page);
        }

        return "products";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute Product product,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        if (result.hasErrors()) {
            reloadPage(session, model, 0);
            return "products";
        }
        String username = (String) session.getAttribute("username");
        productService.save(product, username);
        return "redirect:/products";
    }

    @PostMapping("/products/update")
    public String updateProduct(@Valid @ModelAttribute Product product,
                                BindingResult result,
                                HttpSession session,
                                Model model) {
        if (result.hasErrors()) {
            reloadPage(session, model, 0);
            return "products";
        }
        String username = (String) session.getAttribute("username");
        productService.updateIfOwner(product, username)
                .ifPresentOrElse(
                        p -> {},
                        () -> model.addAttribute("error", "You can only update your own products!")
                );
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        boolean deleted = productService.deleteIfOwner(id, username);
        if (!deleted) {
            model.addAttribute("error", "You can only delete your own products!");
        }
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable int id, Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        productService.findById(id).ifPresent(p -> {
            if (p.getCreatedBy().equals(username)) {
                model.addAttribute("product", p);
            } else {
                model.addAttribute("error", "You can only edit your own products!");
            }
        });
        reloadPage(session, model, 0);
        return "products";
    }

    private void reloadPage(HttpSession session, Model model, int page) {
        String username = (String) session.getAttribute("username");
        Integer roleObj = (Integer) session.getAttribute("role");
        int role = roleObj != null ? roleObj : 2;

        model.addAttribute("username", username);
        model.addAttribute("isManager", role == 1);

        if (role == 1) {
            model.addAttribute("top5Products", productService.findTop5ByPriceDesc());
        } else {
            model.addAttribute("suppliers", supplierService.findAll());
            Page<Product> productPage = productService.findAllPaginated(page);
            model.addAttribute("productPage", productPage);
            model.addAttribute("currentPage", page);
        }
    }
}