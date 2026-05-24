package in.rohanIT.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.rohanIT.ecommerce.entity.ProductCategory;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {

}