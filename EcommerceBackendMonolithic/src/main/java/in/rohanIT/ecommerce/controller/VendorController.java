package in.rohanIT.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.rohanIT.ecommerce.dto.ProductDto;
import in.rohanIT.ecommerce.dto.ProductResponseDto;
import in.rohanIT.ecommerce.dto.VendorDto;
import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.response.ApiResponse;
import in.rohanIT.ecommerce.services.VendorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    
    // ================= REGISTER =================

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody VendorDto vendorDto) {

        String response = vendorService.vendorRegistration(vendorDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, response, null));
    }


    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Vendors>> login(
            @RequestBody VendorDto vendorDto) {

        Vendors vendor = vendorService.login(vendorDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Login Successful", vendor));
    }


    // ================= CHANGE PASSWORD =================

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(
            @RequestBody VendorDto vendorDto) {

        String response = vendorService.changePw(vendorDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, null));
    }


    // ================= GET PRODUCTS =================

    @PostMapping("/products")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProducts(
            @RequestBody Vendors vendor) {

        List<ProductResponseDto> products = vendorService.getProducts(vendor);

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Products Fetched", products));
    }


    // ================= ADD PRODUCT =================

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse<Product>> addProduct(
            @RequestBody ProductDto productDto) {

        Product product = vendorService.addProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Product Added", product));
    }


    // ================= DELETE PRODUCT =================

    @DeleteMapping("/delete-product")
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @RequestBody ProductDto productDto) {

        String response = vendorService.delAllMyProducts(productDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, null));
    }
    
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @PathVariable int id) {

        String response = vendorService.delProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, null));
    }


    // ================= UPDATE PRODUCT =================

    @PutMapping("/update-product")
    public ResponseEntity<ApiResponse<String>> updateProduct(
            @RequestBody ProductDto productDto) {

        String response = vendorService.updateProduct(productDto);

        return ResponseEntity.ok(
                new ApiResponse<>(true, response, null));
    }
    
    @GetMapping("/getVendor/{email}")
    public Vendors getVendor(@PathVariable String email) {
        return vendorService.getVendor(email);
    }
}