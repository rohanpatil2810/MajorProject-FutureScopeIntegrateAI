package in.rohanIT.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.services.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/products") // ✅ clean base path
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ 1. Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // ✅ 2. Get products by category
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
}