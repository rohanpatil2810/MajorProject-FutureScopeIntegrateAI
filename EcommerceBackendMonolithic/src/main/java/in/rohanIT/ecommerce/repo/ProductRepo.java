//package in.rohanIT.ecommerce.repo;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import in.rohanIT.ecommerce.entity.Product;
//
//@Repository
//public interface ProductRepo extends JpaRepository<Product, Integer> {
//
//	List<Product> findByCategoryId(int id);
//	
//}
package in.rohanIT.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.rohanIT.ecommerce.entity.Product;
import jakarta.transaction.Transactional;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByCategory_CategoryId(int categoryId);
//    List<Product> findByVendors(int vendorId);
    List<Product> findByVendors_Id(int vendorId);
//    Product deleteByVendors(int vendorId);
    long deleteByVendors_Id(int vendorId);
    void deleteById(int id);
    
    @Transactional
    @Modifying
    @Query("""
            UPDATE Product p
            SET p.name = :name,
                p.description = :description,
                p.price = :price,
                p.quantity = :quantity,
                p.category = :category,
                p.vendors = :vendors
            WHERE p.id = :id
            """)
    int updateProduct(
            @Param("id") int id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("price") double price,
            @Param("quantity") int quantity,
            @Param("category") in.rohanIT.ecommerce.entity.ProductCategory category,
            @Param("vendors") in.rohanIT.ecommerce.entity.Vendors vendors
    );
}