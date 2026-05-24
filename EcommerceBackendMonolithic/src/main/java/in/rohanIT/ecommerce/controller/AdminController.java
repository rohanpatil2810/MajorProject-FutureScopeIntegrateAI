package in.rohanIT.ecommerce.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.rohanIT.ecommerce.dto.AdminDto;
import in.rohanIT.ecommerce.entity.Admin;
import in.rohanIT.ecommerce.entity.CustomerOrder;
import in.rohanIT.ecommerce.entity.Product;
import in.rohanIT.ecommerce.entity.Vendors;
import in.rohanIT.ecommerce.response.ApiResponse;
import in.rohanIT.ecommerce.services.AdminService;
import okhttp3.Response;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    
    // =========================
    // SAVE ADMIN
    // =========================
    
    @PostMapping("/adminLogin")
    public ResponseEntity<ApiResponse<Admin>> login(@RequestBody AdminDto adminDto){
    	Admin adminLogin = adminService.login(adminDto);
    	return ResponseEntity.ok(new ApiResponse<>(true,"Login Successfull", adminLogin));
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveAdmin(
            @RequestBody AdminDto adminDto){

        try {

            Admin savedAdmin = adminService.saveAdmin(adminDto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedAdmin);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed To Save Admin : " + e.getMessage());
        }
    }

    
    // =========================
    // GET ALL ORDERS
    // =========================
    
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(){

        try {

            List<CustomerOrder> allOrders =
                    adminService.getAllOrders();

            if(allOrders.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Orders Found");
            }

            return ResponseEntity.ok(allOrders);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something Went Wrong : " + e.getMessage());
        }
    }

    
    // =========================
    // GET ALL PRODUCTS
    // =========================
    
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){

        try {

            List<Product> allProducts =
                    adminService.getAllProducts();

            if(allProducts.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Products Found");
            }

            return ResponseEntity.ok(allProducts);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something Went Wrong : " + e.getMessage());
        }
    }

    
    // =========================
    // GET ALL VENDORS
    // =========================
    
    @GetMapping("/vendors")
    public ResponseEntity<?> getAllVendors(){

        try {

            List<Vendors> allVendors =
                    adminService.getAllVendors();

            if(allVendors.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Vendors Found");
            }

            return ResponseEntity.ok(allVendors);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something Went Wrong : " + e.getMessage());
        }
    }

    
    // =========================
    // FILTER ORDERS BETWEEN DATES
    // =========================
    
    @GetMapping("/orders/filter")
    public ResponseEntity<?> getOrdersBetweenDates(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate){

        try {

            // Convert LocalDate -> LocalDateTime
            
            LocalDateTime startDate =
                    fromDate.atStartOfDay();

            LocalDateTime endDate =
                    toDate.atTime(LocalTime.MAX);

            List<CustomerOrder> filteredOrders =
                    adminService.getSortedOrders(
                            startDate,
                            endDate
                    );

            if(filteredOrders.isEmpty()) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No Orders Found Between Given Dates");
            }

            return ResponseEntity.ok(filteredOrders);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something Went Wrong : " + e.getMessage());
        }
    }
}